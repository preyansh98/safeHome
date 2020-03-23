package com.pkaushik.safeHome;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.pkaushik.safeHome.controller.UserController;
import com.pkaushik.safeHome.model.Schedule;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.SafeHomeUser;
import com.pkaushik.safeHome.model.UserRole;
import com.pkaushik.safeHome.model.Walker;
import static com.pkaushik.safeHome.utils.TestConstants.*;

import com.pkaushik.safeHome.repository.SafeHomeUserRepository;
import com.pkaushik.safeHome.repository.StudentRepository;
import com.pkaushik.safeHome.repository.WalkerRepository;
import com.pkaushik.safeHome.service.UserAuthServiceIF;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SafeHomeApplicationTests {

	@Autowired
	private UserController UserController;

	@Autowired
	private UserAuthServiceIF userAuthService;

	@Autowired
	private SafeHomeUserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private WalkerRepository walkerRepository;

	@Before
	public void clearDB() {
		studentRepository.deleteAll();
		walkerRepository.deleteAll();
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	//adding tests for register. 
	@Test
	public void testValidRegisterAsStudent() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, false);
		SafeHomeUser userCreated = SafeHomeUser.getUser(testValidMcgillID); 
		assertNotNull(userCreated);

		List<UserRole> userRolesForUserCreated = userCreated.getRoles(); 
		assertEquals(1, userRolesForUserCreated.size()); //should be registered as only student

		//ensure role is student
		for(UserRole role : userRolesForUserCreated){
			if(role instanceof Walker){
				fail("A walker was created"); 
			}
		}
        assertThat(userRolesForUserCreated.get(0)).isInstanceOf(Student.class);
	}

	@Test
	public void testInvalidRegisterAsStudent() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Please ensure you have entered correct credentials.");
		userAuthService.registerService(testValidPhoneNo, testInvalidMcgillID, true);
	}

	@Test
	public void testValidRegisterAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll();
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, true);
		SafeHomeUser userCreated = SafeHomeUser.getUser(testValidMcgillID); 
		List <UserRole> userRolesForUserCreated = userCreated.getRoles(); 
		assertEquals(2, userRolesForUserCreated.size());
		int counter = 0; 
		for(UserRole role : userRolesForUserCreated){
			if(role instanceof Student){
				counter++; 
				break; 
			}
		}
		assertThat(userRolesForUserCreated.get(counter)).isInstanceOf(Walker.class); 
	}

	//test for registering same user twice. 

	/**
	 * TESTS FOR LOGIN
	 * @throws RuntimeException
	 */
	@Test
	public void testLogInAsStudentOnly() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, false);
		UserController.login(testValidMcgillID, false);
		UserRole currentRole = SafeHomeApplication.getLoggedInUsersMap().get(testValidMcgillID);
		assertThat(currentRole).isInstanceOf(Student.class); 
	}

	@Test
	public void testFailLogInAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, false);

		String errorMessage = "You are not signed up as a Walker."; 
		expectedEx.expect(IllegalAccessError.class);
		expectedEx.expectMessage(errorMessage); 
		UserController.login(testValidMcgillID, true); 
	}

	@Test
	public void testPassLogInAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, true);
		UserController.login(testValidMcgillID, true); 

		//user is registered with only one role. 
		assertThat(SafeHomeApplication.getLoggedInUsersMap().get(testValidMcgillID)).isInstanceOf(Walker.class); 
	}

	@Test
	public void testLogInWithoutRegister() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("The user does not exist. Please create an account first.");
		UserController.login(124124, true);
	}

	@Test
	public void testLogOutAfterLogIn() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, true);
		UserController.login(testValidMcgillID, true); 
		assertThat(SafeHomeApplication.getLoggedInUsersMap().get(testValidMcgillID)).isInstanceOf(Walker.class); 
		UserController.logout(testValidMcgillID);
		assertNull(SafeHomeApplication.getLoggedInUsersMap().get(testValidMcgillID)); 
	}

	//Query Object Tests
	@Test
	public void testUserMapList() throws RuntimeException{
		SafeHomeApplication.resetAll();
		System.gc(); 
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID + 1, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID + 2, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID + 3, true);

		//should be 4 users
		int mapsize = SafeHomeUser.getUserMap().size();
		assertEquals(4, mapsize);
	}

	@Test
	public void testUserQueryList() throws RuntimeException{
		SafeHomeApplication.resetAll();
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID + 1, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID + 2, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID + 3, true);

		//should be 4 users
		List<SafeHomeUser> users = userRepository.findAll();
		assertEquals(4, users.size());
	}

	@Test
	public void testWalkerQueryList() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+1, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+2, true);

		//3 walkers
		List<Walker> walkers = walkerRepository.findAll();
		List<Student> students = studentRepository.findAll();
		List<SafeHomeUser> users = userRepository.findAll();
		assertEquals(3,users.size());
		assertEquals(3, walkers.size()); 
		assertEquals(0, students.size());
	}

	@Test
	public void testStudentQueryList() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, false);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, false);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, false);

		//3 walkers
		List<Walker> walkers = walkerRepository.findAll();
		List<Student> students = studentRepository.findAll();
		assertEquals(3, students.size());
		assertEquals(0, walkers.size()); 
	}

	@Test
	//TODO: Configure for new schedule constructor
	public void testWalkerHasSchedule() throws RuntimeException{
		SafeHomeApplication.resetAll();
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, true);
		Walker currWalker = (Walker) Walker.getRole(testValidMcgillID);

		Schedule currWalkerSchedule = new Schedule(LocalDateTime.of(LocalDate.of(2019,12,01), LocalTime.of(0,0,0))
											, LocalDateTime.of(LocalDate.of(2019,15,30), LocalTime.of(0,0,0)));

		currWalker.setSchedule(currWalkerSchedule);
		assertNotNull(currWalker.getSchedule());

		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+1, true);
		Walker currWalker2 = (Walker) Walker.getRole(testValidMcgillID+1);
		assertNotNull(currWalker2);
		assertNull(currWalker2.getSchedule());
	}

	@Test
	public void testStudentCanSelectAWalker() throws RuntimeException{
		SafeHomeApplication.resetAll(); 

		//4 users register
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+1, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+2, true);
		userAuthService.registerService(testValidPhoneNo, testValidMcgillID+3, true);

		//2 walkers login
		UserController.login(testValidMcgillID+2, true);
		UserController.login(testValidMcgillID+3, true);
		
		//1 student logs in
		UserController.login(testValidMcgillID, false); 

		//student must be able to create a request and select a walker for it. 
		
	}

}
