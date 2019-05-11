package com.pkaushik.safeHome.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public static void register(int phoneNo, int mcgillID){
	
	//Validation checks. 

	//if there is no error with validation, create the roles

}


public static void login(int mcgillID, boolean loginAsWalker){
	UserRole currentRole = SafeHomeApplication.getCurrentUserRole();
	SafeHomeApplication.resetAll();
	User user = null; 
	try{
	user = User.getUser(mcgillID);
	}
	catch(NullPointerException e){
		throw new IllegalArgumentException("The user does not exist. Please create an account first.");
	}

	if(currentRole!=null){
		throw new IllegalAccessError("A user is already logged in.");
	}
	else{
		//depending on loginasWalker set currentUserRole.
		List<UserRole> roles = user.getRoles(); 
		//if loginAsWalker is true
		if(loginAsWalker){
		for(UserRole role : roles){
			//check if the user has a walker role
			if(role instanceof Walker) SafeHomeApplication.setCurrentUserRole(role);
		}
	}
		else{
		//login as student
		for(UserRole role : roles){
			if(role instanceof Student) SafeHomeApplication.setCurrentUserRole(role); 
		}
	}
	}
}
}
