package com.pkaushik.safeHome;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.pkaushik.safeHome.utils.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import com.pkaushik.safeHome.controller.SafeHomeController;
import com.pkaushik.safeHome.controller.UserController;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.UserRole;

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
