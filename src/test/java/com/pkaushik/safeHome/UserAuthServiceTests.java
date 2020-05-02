package com.pkaushik.safeHome;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.SafeHomeUser;
import com.pkaushik.safeHome.model.UserRole;
import com.pkaushik.safeHome.model.Walker;
import static com.pkaushik.safeHome.utils.TestConstants.*;

import com.pkaushik.safeHome.service.UserAuthServiceIF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAuthServiceTests {

	@Autowired
	private UserAuthServiceIF userAuthService;

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


}