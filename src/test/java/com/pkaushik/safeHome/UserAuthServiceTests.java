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
import com.pkaushik.safeHome.model.SafeHomeUser;
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
public class UserAuthServiceTests {

    public void testValidRegisterAsStudent() throws RuntimeException{
		SafeHomeApplication.resetAll(); 
        UserController ctrl = new UserController(); 
        
        ctrl.regWithService(testValidPhoneNo, testValidMcgillID, false);
        
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