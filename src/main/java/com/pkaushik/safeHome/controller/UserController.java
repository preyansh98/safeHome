package com.pkaushik.safeHome.controller;

import java.math.BigInteger;
import java.util.List;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.User;
import com.pkaushik.safeHome.model.UserRole;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController{
    
//User Stuff
//TODO: can't register with duplicates.
public static void register(BigInteger phoneNo, int mcgillID, boolean registerForWalker){
	SafeHome safeHome = SafeHomeApplication.getSafeHome();
	User tmpUser = null; 
	try{
		tmpUser = new User(phoneNo, mcgillID, safeHome); 
	}
	catch(Exception e){
		tmpUser = null; 
		System.gc();
	}
	if(!(tmpUser instanceof User)){
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
			User user = new User(phoneNo, mcgillID, safeHome); 
			
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
	User user = User.getUser(mcgillID);
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
 * Logout -> based on mcgill id, remove them from the loggedin user list, and change their status. 
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