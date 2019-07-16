package com.pkaushik.safeHome;

import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.model.enumerations.RequestStatus;
import com.pkaushik.safeHome.repository.StudentRepository;
import com.pkaushik.safeHome.repository.WalkerRepository;
import com.pkaushik.safeHome.service.RequestServiceIF;
import com.pkaushik.safeHome.service.StudentServiceIF;
import com.pkaushik.safeHome.service.UserAuthServiceIF;
import com.pkaushik.safeHome.service.WalkerServiceIF;
import com.pkaushik.safeHome.service.impl.UserAuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.pkaushik.safeHome.utils.TestConstants.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import com.pkaushik.safeHome.model.enumerations.WalkerStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class requestsTests {

    @Autowired
    private RequestServiceIF requestService;

    @Autowired
    private StudentServiceIF studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private WalkerRepository walkerRepository;

    @Autowired
    private WalkerServiceIF walkerService;

    @Autowired
    private UserAuthServiceIF userAuthService;


@Test
public void createARequest() throws RuntimeException{
    //register couple users.
    	//4 users register
        oneStudentAndFourWalkersLogged();
        assertEquals(SafeHomeApplication.getCurrentRequestsMap().size(), 0);
        ///////////////////
        requestService.createRequestService(testValidMcgillID,
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
        studentService.selectWalkerForRequestService(testValidMcgillID, testValidMcgillID+2);
        assertEquals(SafeHomeApplication.getOpenAssignmentsMap().size(), 1);

        //test breach to get uuid
        UUID assignmentUUID = (UUID)
        SafeHomeApplication.getOpenAssignmentsMap().keySet().toArray()[0];
        Walker walker = (Walker) Walker.getRole(testValidMcgillID+2);

        //assignment is open.
        //assume walker is pinged about it, and responds.

        //Walker should not have any assignments before
        assertThat(walker.getCurrentAssignment()).isNull();
        walkerService.acceptAssignmentService(testValidMcgillID+2, assignmentUUID);
        assertThat(walker.getCurrentAssignment()).isNotNull(); //walker should have assignment
        assertEquals(WalkerStatus.ASSIGNED, walker.getStatus()); //walker status should change
        assertEquals(SafeHomeApplication.getOpenAssignmentsMap().size(), 0); //assignment isn't open should be deleted

        //Is student in accepted assignment the same as the one who created request
        Student studentWhoCreatedReq = (Student) Student.getRole(testValidMcgillID);

        Student studentInAssignment = walker.getCurrentAssignment().getRequest().getStudent();

        assertEquals(studentWhoCreatedReq.hashCode(), studentInAssignment.hashCode());


    }

    @Test
    public void getPotentialWalkers() throws RuntimeException{

        oneStudentAndFourWalkersLogged();

        requestService.createRequestService(testValidMcgillID,
                testPickupLatitude, testPickupLongitude, testDestinationLatitude, testDestinationLongitude);

        UserRole role = SafeHomeApplication.getLoggedInUsersMap().get(testValidMcgillID);

        assertThat(role).isInstanceOf(Student.class);
        Student student = (Student) role;

        assertNotNull(student.getRequest());
        assertNull(student.getRequest().getAssignment());
        assertEquals(student.getRequest().getRequestStatus(), RequestStatus.CREATED);

        System.out.println(studentService.getAllPotentialWalkers(testValidMcgillID));
}


    private void oneStudentAndFourWalkersLogged() {
        userAuthService.registerService(testValidPhoneNo, testValidMcgillID, false);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+1, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+2, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+3, true);
        userAuthService.registerService(testValidPhoneNo, testValidMcgillID+4, true);
        userAuthService.registerService(testValidPhoneNo, testValidMcgillID+5, true);
        userAuthService.registerService(testValidPhoneNo, testValidMcgillID+6, true);
        userAuthService.registerService(testValidPhoneNo, testValidMcgillID+7, true);
        userAuthService.registerService(testValidPhoneNo, testValidMcgillID+8, true);



		//2 walkers login
		userAuthService.loginService(testValidMcgillID+2, true);
		userAuthService.loginService(testValidMcgillID+3, true);
        userAuthService.loginService(testValidMcgillID+4, true);
        userAuthService.loginService(testValidMcgillID+5, true);
        userAuthService.loginService(testValidMcgillID+6, true);
        userAuthService.loginService(testValidMcgillID+7, true);
        userAuthService.loginService(testValidMcgillID+8, true);


		//1 student logs in
        userAuthService.loginService(testValidMcgillID, false);
    }

}
