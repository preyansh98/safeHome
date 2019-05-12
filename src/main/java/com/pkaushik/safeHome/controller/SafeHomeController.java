package com.pkaushik.safeHome.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.*;

@RestController
public class SafeHomeController {
	
@RequestMapping("/user")
public Walker returnIndex() {
	Walker testWalker = new Walker(null); 
	return testWalker; 
}
	
//Modifier Methods

//Create
public static void createSpecificRequest(String pickupLocationEntered, String destinationEntered, String pickupTimeEntered) {
	if(SafeHomeApplication.getCurrentUserRole() instanceof Walker){
		throw new IllegalArgumentException("A Walker can not create a pickup request"); 
	}
	//only a student can create a request

	Student student = (Student) SafeHomeApplication.getCurrentUserRole();
	SpecificRequest specificRequest = null; 
	Location pickupLocation = null; 
	Location destination = null; 
	DateTime pickupTime = null; 

	try{
	//
	//   INSERT 
	// 		CODE
	//			to create Location, DateTime variables correspondingly for the pickuptime and destination
	//
		specificRequest = new SpecificRequest(student, pickupLocation, destination, pickupTime);
	}
	catch(Exception e){
		throw new IllegalArgumentException("a");
	}
}

public static void changeRequestDetails() {
	
}

public static void cancelRequest() {
	
}

public static void addWalker() {
	
}

public static void assignWalker() {
	
}

//User Stuff
public static void register(BigInteger phoneNo, int mcgillID, boolean registerForWalker){
	UserRole currentRole = SafeHomeApplication.getCurrentUserRole();
	if(currentRole!=null) throw new IllegalArgumentException("Can not register when a user is logged in. Please log out."); 
	User tmpUser = null; 
	try{
		tmpUser = new User(phoneNo, mcgillID); 
	}
	catch(Exception e){
		tmpUser = null; 
	}
	if(!(tmpUser instanceof User)){
		//validation problems occur. 
		throw new IllegalArgumentException("Please ensure you have entered correct credentials."); 
	}
	else{
		Student student = null; 
		Walker walker = null; 
		try{
			SafeHome safeHome = SafeHomeApplication.getSafeHome(); 
			student = new Student(safeHome); 
			//validation for user
			User user = new User(phoneNo, mcgillID); 
			
			if(!(user.addRole(student))) throw new RuntimeException("could not add role to user");

			if(registerForWalker){
				walker = new Walker(safeHome);
				user.addRole(walker); 
			}	 
		}
		catch(Exception e){
			//if an exception delete all user, walker, student instances
			walker = null; 
			student = null; 

			//TODO: add other error handling. 
		}
	}
}

public static void login(int mcgillID, boolean loginAsWalker){
	UserRole currentRole = SafeHomeApplication.getCurrentUserRole();
	SafeHomeApplication.resetAll();
	User user = User.getUser(mcgillID);
	if(user == null) throw new IllegalArgumentException("The user does not exist. Please create an account first.");
	

	if(currentRole!=null){
		throw new IllegalAccessError("A user is already logged in.");
	}
	else{
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
		if(isRegisteredAsWalker == false) {throw new IllegalAccessError("You are not signed up as a Walker.");}
		SafeHomeApplication.setCurrentUserRole(walkerRole);
	}
		else{
		//login as student
		UserRole studentRole = null; 
		for(UserRole role : roles){
			if(role instanceof Student) studentRole = role; 
		}
		SafeHomeApplication.setCurrentUserRole(studentRole); 
	}
	}
}
public static void logout(){

	}
}
