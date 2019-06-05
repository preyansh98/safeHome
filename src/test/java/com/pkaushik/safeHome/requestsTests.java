package com.pkaushik.safeHome;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.pkaushik.safeHome.utils.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import com.pkaushik.safeHome.controller.SafeHomeController;
import com.pkaushik.safeHome.controller.UserController;
import com.pkaushik.safeHome.controller.WalkerModController;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.User;
import com.pkaushik.safeHome.model.UserRole;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class requestsTests {

@Test
public void createARequest() throws RuntimeException{
    //register couple users. 
    	//4 users register
        oneStudentAndFourWalkersLogged(); 
        assertEquals(SafeHomeApplication.getCurrentRequestsMap().size(), 0); 
        ///////////////////
        SafeHomeController.createSpecificRequest(testValidMcgillID, 
        testPickupLatitude, testPickupLongitude, testDestinationLatitude, testDestinationLongitude);

        assertEquals(SafeHomeApplication.getCurrentRequestsMap().size(), 1); 
        UserRole role = SafeHomeApplication.getLoggedInUsersMap().get(testValidMcgillID);

        assertThat(role).isInstanceOf(Student.class); 
        Student student = (Student) role; 

        assertNotNull(student.getRequest());
        assertEquals(testPickupLatitude, student.getRequest().getPickupLocation().getLatitude(), 0.001); 
    }

    @Test
    public void acceptARequest() throws RuntimeException{
        createARequest();
        assertEquals(SafeHomeApplication.getCurrentRequestsMap().size(), 1); 

        //request is created. 
        //select a walker 
        SafeHomeController.selectWalker(testValidMcgillID, testValidMcgillID+2);
        assertEquals(SafeHomeApplication.getOpenAssignmentsMap().size(), 1);

        //test breach to get uuid
        UUID assignmentUUID = (UUID) 
        SafeHomeApplication.getOpenAssignmentsMap().keySet().toArray()[0];
        Walker walker = Walker.getWalker(testValidMcgillID+2); 

        //assignment is open.
        //assume walker is pinged about it, and responds. 

        //Walker should not have any assignments before
        assertThat(walker.getCurrentAssignment()).isNull();
        WalkerModController.acceptAssignment(testValidMcgillID+2, assignmentUUID);
        assertThat(walker.getCurrentAssignment()).isNotNull(); //walker should have assignment
        assertEquals(WalkerStatus.ASSIGNED, walker.getStatus()); //walker status should change
        assertEquals(SafeHomeApplication.getOpenAssignmentsMap().size(), 0); //assignment isn't open should be deleted

        //Is student in accepted assignment the same as the one who created request
        Student studentWhoCreatedReq = (Student) User.getUser(testValidMcgillID).getRoles()
                                                .stream().filter((x) -> x instanceof Student)
                                                .findAny()
                                                .orElse(null); 

        Student studentInAssignment = walker.getCurrentAssignment().getRequest().getStudent();

        assertEquals(studentWhoCreatedReq.hashCode(), studentInAssignment.hashCode()); 


    }


    private void oneStudentAndFourWalkersLogged() {
        UserController.register(testValidPhoneNo, testValidMcgillID, true);
		UserController.register(testValidPhoneNo, testValidMcgillID+1, true);
		UserController.register(testValidPhoneNo, testValidMcgillID+2, true);
		UserController.register(testValidPhoneNo, testValidMcgillID+3, true);

		//2 walkers login
		UserController.login(testValidMcgillID+2, true);
		UserController.login(testValidMcgillID+3, true);
		
		//1 student logs in
        UserController.login(testValidMcgillID, false); 
    }

}
