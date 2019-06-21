package com.pkaushik.safeHome.controller.revised;

import java.math.BigInteger;
import java.util.List;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.SafeHomeUser;
import com.pkaushik.safeHome.model.UserRole;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;

import com.pkaushik.safeHome.service.impl.UserAuthService;
import com.pkaushik.safeHome.validation.impl.inputValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_ID;
import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_PHONE;

@RestController
@RequestMapping("/api")
public class UserControllerUsingServ{
    
//User Stuff
//TODO: can't register with duplicates. db takes care of this?

	@Autowired
    private UserAuthService userAuthService;
    
    @Autowired
    private inputValidation inputValidation; 
	
    @PostMapping("/register/{phoneNo}/{mcgillID}/{regAsWlkr}")
	public String regWithService(@PathVariable("phoneNo") BigInteger phoneNo, @PathVariable("mcgillID")int mcgillID, @PathVariable("regAsWlkr") boolean regAsWlkr){

        //input validation for mcgill ID and phoneNo
        try{
            inputValidation.validateMcgillID(mcgillID);
            inputValidation.validatePhoneNo(phoneNo);
        }
        catch(IllegalArgumentException e){
            //return error response.  
        }

		if(new Integer(mcgillID).toString().trim().length() != MAX_DIGITS_FOR_ID){
			//return error resp
			return "errorrrr";
		}

		//delegate state valid to service
		try {
			userAuthService.registerService(phoneNo, mcgillID, regAsWlkr);
		}
		catch(Exception e){
			//catch exceptions from service layer
			//return these resps formatted well.
			return e.getMessage();
		}
		//generate resp
		return "works"; 
	}

	public String loginWithService(int mcgillID, boolean loginAsWalker){

		//check inputs.
		if(new Integer(mcgillID).toString().trim().length() != MAX_DIGITS_FOR_PHONE){
			//return error resp
			return "";
		}

		try{
			userAuthService.loginService(mcgillID, loginAsWalker);
		}
		catch(Exception e){
			//return error resp
			return "nope";
			//generate resp
		}
		//final resp to send.
		return "loginworks"; 
	}

	public String logoutAsService(int mcgillID){

		//check if mcgillID is ok
		if(new Integer(mcgillID).toString().trim().length() != MAX_DIGITS_FOR_ID){
			//error resp
			return "";
		}

		try{
			userAuthService.logoutService(mcgillID);
		}
		catch(Exception e){
			//return error resp
			return "err"; 
			//generate reps
		}
		//final resp to send
		return "loggedout"; 
	}


public static void register(BigInteger phoneNo, int mcgillID, boolean registerForWalker){
	SafeHome safeHome = SafeHomeApplication.getSafeHome();
	SafeHomeUser tmpUser = null; 
	try{
		tmpUser = new SafeHomeUser(phoneNo, mcgillID, safeHome); 
	}
	catch(Exception e){
		tmpUser = null; 
		System.gc();
	}
	if(!(tmpUser instanceof SafeHomeUser)){
		//validation problems occur. 
		throw new IllegalArgumentException("Please ensure you have entered correct credentials."); 
	}
	else{
		SafeHomeApplication.getSafeHome().getUsers().remove(tmpUser); 
		Student student = null; 
		Walker walker = null; 
		System.gc(); 
		try{
			student = new Student(safeHome); 
			//validation for user
			SafeHomeUser user = new SafeHomeUser(phoneNo, mcgillID, safeHome); 
			
			if(!(user.addRole(student))) throw new RuntimeException("could not add role to user");

			if(registerForWalker){
				walker = new Walker(safeHome, false);
				user.addRole(walker); 
			}	 
		}
		catch(Exception e){
			//if an exception delete all user, walker, student instances
			walker = null; 
			student = null; 
			SafeHomeApplication.resetAll(); 
			//TODO: add other error handling. 
		}
	}
}

public static void login(int mcgillID, boolean loginAsWalker){
	SafeHomeApplication.resetAll(); //why do we need this?
	SafeHomeUser user = SafeHomeUser.getUser(mcgillID);
	if(user == null) throw new IllegalArgumentException("The user does not exist. Please create an account first.");
	
		//depending on loginasWalker set currentUserRole.
		List<UserRole> roles = user.getRoles(); 
		//if loginAsWalker is true
		if(loginAsWalker){
		boolean isRegisteredAsWalker = false;
		UserRole walkerRole = null;  
		for(UserRole role : roles){
			//check if the user has a walker role
			if(role instanceof Walker) {walkerRole = role; isRegisteredAsWalker = true;}
		}
		((Walker) walkerRole).setStatus(WalkerStatus.LOGGED_IN);
		if(isRegisteredAsWalker == false) {throw new IllegalAccessError("You are not signed up as a Walker.");}
		if(walkerRole!=null) SafeHomeApplication.logInUser(mcgillID, walkerRole);
	}
		else{
		//login as student
		UserRole studentRole = null; 
		for(UserRole role : roles){
			if(role instanceof Student) studentRole = role; 
		}
		if(studentRole!=null) SafeHomeApplication.logInUser(mcgillID, studentRole);
	}
	}

/**
 * Logout -> based on mcgill mcgillID, remove them from the loggedin user list, and change their status. 
 * @param mcgillID
 */
public static void logout(int mcgillID){	
		if(SafeHomeApplication.getLoggedInUsersMap().isEmpty()) throw new IllegalArgumentException("No users are logged in");

		UserRole loggedInRole = SafeHomeApplication.getLoggedInUsersMap().get(mcgillID); 
		if(loggedInRole == null) throw new IllegalStateException("Logged in user could not be found");
		
		if(loggedInRole instanceof Walker){
			((Walker) loggedInRole).setStatus(WalkerStatus.INACTIVE);
		}
		else if(loggedInRole instanceof Student){
			//student's request should be set to null or cancelled if they have one. 
			//set their status as well to inactive. 
		}
		else{
			return;
		}
		SafeHomeApplication.logOutUser(mcgillID);
	}

public static void switchRole(int mcgillID){
	if(SafeHomeApplication.getLoggedInUsersMap().get(mcgillID) == null) 
		throw new IllegalAccessError("Can't switch roles without first logging in");

	UserRole currRole = SafeHomeApplication.getLoggedInUsersMap().get(mcgillID);
	
	if(currRole instanceof Student){
		logout(mcgillID); 
		login(mcgillID, true); 
	}
	else if(currRole instanceof Walker){
		logout(mcgillID); 
		login(mcgillID, false); 
	}
	else{
		throw new RuntimeException("Current role is neither a walker nor student"); 
	}
	//refreshcontext.
}
}