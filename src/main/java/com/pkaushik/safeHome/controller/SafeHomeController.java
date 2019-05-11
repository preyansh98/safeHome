package com.pkaushik.safeHome.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.*;

@RestController
public class SafeHomeController {
	
@RequestMapping("/user")
public Walker returnIndex() {
	Walker testWalker = new Walker(null, null); 
	return testWalker; 
}
	
//Modifier Methods

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
}
