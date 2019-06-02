package com.pkaushik.safeHome;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import com.pkaushik.safeHome.controller.DTOWalker;
import com.pkaushik.safeHome.controller.QueryController;
import com.pkaushik.safeHome.controller.UserController;
import com.pkaushik.safeHome.model.DateTime;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Schedule;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.User;
import com.pkaushik.safeHome.model.UserRole;
import com.pkaushik.safeHome.model.Walker;
import static com.pkaushik.safeHome.utils.TestConstants.*; 

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SafeHomeApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	//adding tests for register. 
	@Test
	public void testValidRegisterAsStudent() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		
		UserController.register(testValidPhoneNo, testValidMcgillID, false);
		User userCreated = User.getUser(testValidMcgillID); 
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
		UserController.register(testValidPhoneNo, testInvalidMcgillID, true);		
	}

	@Test
	public void testValidRegisterAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll();
		UserController.register(testValidPhoneNo, testValidMcgillID, true);
		User userCreated = User.getUser(testValidMcgillID); 
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
		UserController.register(testValidPhoneNo, testValidMcgillID, false);
		UserController.login(testValidMcgillID, false);
		UserRole currentRole = SafeHomeApplication.getLoggedInUsersMap().get(testValidMcgillID);
		assertThat(currentRole).isInstanceOf(Student.class); 
	}

	@Test
	public void testFailLogInAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		UserController.register(testValidPhoneNo, testValidMcgillID, false); 

		String errorMessage = "You are not signed up as a Walker."; 
		expectedEx.expect(IllegalAccessError.class);
		expectedEx.expectMessage(errorMessage); 
		UserController.login(testValidMcgillID, true); 
	}

	@Test
	public void testPassLogInAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		UserController.register(testValidPhoneNo, testValidMcgillID, true); 
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
		UserController.register(testValidPhoneNo, testValidMcgillID, true); 
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
		UserController.register(testValidPhoneNo, testValidMcgillID, true); 
		UserController.register(testValidPhoneNo, testValidMcgillID + 1, true); 
		UserController.register(testValidPhoneNo, testValidMcgillID + 2, true); 
		UserController.register(testValidPhoneNo, testValidMcgillID + 3, true); 

		//should be 4 users
		int mapsize = User.getUserMap().size();
		assertEquals(4, mapsize);
	}

	@Test
	public void testUserQueryList() throws RuntimeException{
		SafeHomeApplication.resetAll();
		UserController.register(testValidPhoneNo, testValidMcgillID, true); 
		UserController.register(testValidPhoneNo, testValidMcgillID + 1, true); 
		UserController.register(testValidPhoneNo, testValidMcgillID + 2, true); 
		UserController.register(testValidPhoneNo, testValidMcgillID + 3, true); 

		//should be 4 users
		SafeHome safehome = SafeHomeApplication.getSafeHome(); 
		List<User> users = safehome.getUsers(); 
		assertEquals(4, users.size());
	}

	@Test
	public void testWalkerQueryList() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		UserController.register(testValidPhoneNo, testValidMcgillID, true);
		UserController.register(testValidPhoneNo, testValidMcgillID, true);
		UserController.register(testValidPhoneNo, testValidMcgillID, true);

		//3 walkers
		SafeHome safeHome = SafeHomeApplication.getSafeHome(); 
		List<Walker> walkers = safeHome.getWalkers(); 
		List<Student> students = safeHome.getStudents();
		assertEquals(3, walkers.size()); 
		assertEquals(3, students.size());
	}

	@Test
	public void testStudentQueryList() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		UserController.register(testValidPhoneNo, testValidMcgillID, false);
		UserController.register(testValidPhoneNo, testValidMcgillID, false);
		UserController.register(testValidPhoneNo, testValidMcgillID, false);

		//3 walkers
		SafeHome safeHome = SafeHomeApplication.getSafeHome(); 
		List<Walker> walkers = safeHome.getWalkers(); 
		List<Student> students = safeHome.getStudents(); 
		assertEquals(3, students.size());
		assertEquals(0, walkers.size()); 
	}

	/**
	 * Conditions for test: 
	 * 1. Register a walker
	 * 2. Set their attributes
	 * 3. Set their start and end schedules. 
	 * 
	 * note: the schedule is set manually using deprecated constructor.s
	 * @throws RuntimeException
	 */
	@Test
	public void testWalkersDTO() throws RuntimeException{
		SafeHomeApplication.resetAll();
		UserController.register(testValidPhoneNo, testValidMcgillID, true);
		Walker currWalker = Walker.getWalker(testValidMcgillID);
		currWalker.setRating(2);
		Schedule currWalkerSchedule = new Schedule(new DateTime(), new DateTime());
		currWalkerSchedule.setStartDate(02, 11, 2018);
		currWalker.setSchedule(currWalkerSchedule);

		List<DTOWalker> walkers = QueryController.getAllWalkers(); 
		assertEquals(currWalker.getSchedule().getStartDate().getDateTime(), walkers.get(0).getStartDateTime());
	}
	
	@Test
	public void testWalkerHasSchedule() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		UserController.register(testValidPhoneNo, testValidMcgillID, true); 
		Walker currWalker = Walker.getWalker(testValidMcgillID); 
		Schedule currWalkerSchedule = new Schedule(12,01,2019,12,02,2019,15,30,19,00);
		currWalker.setSchedule(currWalkerSchedule);
		assertEquals(true, currWalker.hasSchedule());

		UserController.register(testValidPhoneNo, testValidMcgillID+1, true); 
		Walker currWalker2 = Walker.getWalker(testValidMcgillID+1);
		assertNotNull(currWalker2);
		assertEquals(false, currWalker2.hasSchedule()); 
	}

	@Test
	public void testStudentCanSelectAWalker() throws RuntimeException{
		SafeHomeApplication.resetAll(); 

		//4 users register
		UserController.register(testValidPhoneNo, testValidMcgillID, true);
		UserController.register(testValidPhoneNo, testValidMcgillID+1, true);
		UserController.register(testValidPhoneNo, testValidMcgillID+2, true);
		UserController.register(testValidPhoneNo, testValidMcgillID+3, true);

		//2 walkers login
		UserController.login(testValidMcgillID+2, true);
		UserController.login(testValidMcgillID+3, true);
		
		//1 student logs in
		UserController.login(testValidMcgillID, false); 

		//student must be able to create a request and select a walker for it. 
		
	}
}
