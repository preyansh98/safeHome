package com.pkaushik.safeHome.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.model.Walker.walkerStatus;

@RestController
public class SafeHomeController {
	
//Modifier Methods

//CRUD Request
	
//TODO: think about method implementation, parameters. 
public static void createSpecificRequest(String pickupLocationEntered, String destinationEntered,
		String pickupTimeEntered) {
	if(SafeHomeApplication.getCurrentUserRole() instanceof Walker){
		throw new IllegalArgumentException("A Walker can not create a pickup request"); 
	}
	//only a student can create a request

	Student student = (Student) SafeHomeApplication.getCurrentUserRole();
	SpecificRequest specificRequest = null; 
	//TODO: implementation for creating a location and getting distance etc
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
		student.setRequest(specificRequest);
		SafeHomeApplication.setCurrentRequest(specificRequest); 
	}
	catch(Exception e){
		throw new IllegalArgumentException("a");
	}
}

public static void changeRequestDetails() {
	SpecificRequest currentRequest = SafeHomeApplication.getCurrentRequest(); 
	if(currentRequest == null){
		throw new RuntimeException("Can't modify uncreated request.");
	}

}

public static void cancelRequest() {
	if(SafeHomeApplication.getCurrentRequest() == null) throw new RuntimeException("You can't cancel a request when no request exists"); 
	
	SafeHomeApplication.setCurrentRequest(null);
	//remove req from student, remove req from walker
	
	
}

public static void addWalker() {
	
}

public static void selectWalker(int studentID, int walkerID) {
	if(SafeHomeApplication.getCurrentRequest() == null) throw new RuntimeException("Can't select walker without creating a request first"); 
	
	User studentUser = User.getUser(studentID);
	User walkerUser = User.getUser(walkerID); 
	
	if(studentUser.equals(walkerUser)) throw new RuntimeException("You can't select yourself as walker"); 
	
	UserRole studentRole = null, walkerRole = null; 
	
	for(UserRole role : studentUser.getRoles()) {
		if(role instanceof Student) {
			studentRole = (Student) role; 
			break; 
		}
	}
	if(studentRole == null) throw new RuntimeException("Unexpected error, you are not registered as a student");
	for(UserRole role : walkerUser.getRoles()) {
		if(role instanceof Walker) {
			walkerRole = (Walker) role; 
			break; 
		}
	}
	if(walkerRole == null) throw new RuntimeException("The walker selected does not exist");
	
	SpecificRequest currRequest = ((Student) studentRole).getRequest(); 
	if(!currRequest.equals(SafeHomeApplication.getCurrentRequest())) throw new RuntimeException("The request must be set "
			+ "first."); 
	
	((Walker) walkerRole).setRequestMade(currRequest);
	
}

//CRUD WalkerSchedule
public static void setWalkerSchedule(int mcgillID, int startDay, int startMonth, int startYear, 
	int endDay, int endMonth, int endYear, int startHour, int startMin, int endHour, int endMin){
		User user = User.getUser(mcgillID);
		Walker walkerRole = null; 
		List<UserRole> roles = user.getRoles(); 
		
		for(UserRole role : roles) {
			if(role instanceof Walker) {
				walkerRole = (Walker) role; 
				break; 
			}
		}
		if(walkerRole == null) throw new RuntimeException("The walker whose schedule you are trying to set does not exist");
		
		Schedule newSchedule = new Schedule(startDay, startMonth, startYear, endDay, endMonth, endYear, 
				startHour, startMin, endHour, endMin);
		walkerRole.setSchedule(newSchedule);
	}

/**
 * Enter all params as -1, apart from the one that wants to be changed. 
 * @param mcgillID
 * @param startDay
 * @param startMonth
 * @param startYear
 * @param endDay
 * @param endMonth
 * @param endYear
 * @param startHour
 * @param startMin
 * @param endHour
 * @param endMin
 */
public static void changeWalkerSchedule(int mcgillID, int startDay, int startMonth, int startYear, 
int endDay, int endMonth, int endYear, int startHour, int startMin, int endHour, int endMin){
	//TODO: Add checks to ensure parameters follow date-time convention. 
	User user = User.getUser(mcgillID);
	Walker walkerRole = null; 
	List<UserRole> roles = user.getRoles(); 
	for(UserRole role : roles){
		if(role instanceof Walker) {
			walkerRole = (Walker) role; 
			break;
		}
	}
	
	Schedule currentSchedule = walkerRole.getSchedule(); 
	if(currentSchedule == null) throw new RuntimeException("No schedule exists to update. Please create a schedule first"); 

	//brute force
	if(startDay >= 0) currentSchedule.setStartDay(startDay);
	if(startMonth >= 0) currentSchedule.setStartMonth(startMonth);
	if(startYear >= 0) currentSchedule.setStartYear(startYear);
	if(endDay >= 0) currentSchedule.setEndDay(endDay);
	if(endMonth >= 0) currentSchedule.setEndMonth(endMonth);
	if(endYear >= 0) currentSchedule.setEndYear(endYear);
	if(startHour >= 0) currentSchedule.setStartHour(startHour);
	if(startMin >= 0) currentSchedule.setStartMin(startMin);
	if(endHour >= 0) currentSchedule.setEndHour(endHour);
	if(endMin >= 0) currentSchedule.setEndYear(endYear);
}

//CRUD Walker 

//CRUD Student






}
