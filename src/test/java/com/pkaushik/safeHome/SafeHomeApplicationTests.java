package com.pkaushik.safeHome;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.List;

import com.pkaushik.safeHome.controller.SafeHomeController;
import com.pkaushik.safeHome.model.*;

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

	//Static variables for test
	private static final BigInteger testValidPhoneNo = new BigInteger("4389247381"); 
	private static final int testValidMcgillID = 260790402; 
	private static final BigInteger testInvalidPhoneNo = new BigInteger("438738"); 
	private static final int testInvalidMcgillID = 26079040; 


	//adding tests for register. 
	@Test
	public void testValidRegisterAsStudent() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID, false);
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
		SafeHomeController.register(testValidPhoneNo, testInvalidMcgillID, true);		
	}

	@Test
	public void testValidRegisterAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll();
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID, true);
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
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID, false);
		SafeHomeController.login(testValidMcgillID, false);
		UserRole currentRole = SafeHomeApplication.getCurrentUserRole();

		assertThat(currentRole).isInstanceOf(Student.class); 
	}

	@Test
	public void testFailLogInAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID, false); 

		String errorMessage = "You are not signed up as a Walker."; 
		expectedEx.expect(IllegalAccessError.class);
		expectedEx.expectMessage(errorMessage); 
		SafeHomeController.login(testValidMcgillID, true); 
	}

	@Test
	public void testPassLogInAsWalker() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID, true); 
		SafeHomeController.login(testValidMcgillID, true); 

		//user is registered with only one role. 
		assertThat(SafeHomeApplication.getCurrentUserRole()).isInstanceOf(Walker.class); 
	}

	@Test
	public void testLogInWithoutRegister() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("The user does not exist. Please create an account first.");
		SafeHomeController.login(124124, true);
	}

	@Test
	public void testLogOutAfterLogIn() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID, true); 
		SafeHomeController.login(testValidMcgillID, true); 
		assertThat(SafeHomeApplication.getCurrentUserRole()).isInstanceOf(Walker.class); 
		SafeHomeController.logout();
		assertNull(SafeHomeApplication.getCurrentUserRole()); 
	}

	//Query Object Tests
	@Test
	public void testUserMapList() throws RuntimeException{
		SafeHomeApplication.resetAll();
		System.gc(); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID, true); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID + 1, true); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID + 2, true); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID + 3, true); 

		//should be 4 users
		int mapsize = User.getUserMap().size();
		assertEquals(4, mapsize);
	}

	@Test
	public void testUserQueryList() throws RuntimeException{
		SafeHomeApplication.resetAll();
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID, true); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID + 1, true); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID + 2, true); 
		SafeHomeController.register(testValidPhoneNo, testValidMcgillID + 3, true); 

		//should be 4 users
		SafeHome safehome = SafeHomeApplication.getSafeHome(); 
		List<User> users = safehome.getUsers(); 
		assertEquals(4, users.size());
	}
}
