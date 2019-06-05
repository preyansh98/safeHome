package com.pkaushik.safeHome.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.Location;
import com.pkaushik.safeHome.model.Schedule;
import com.pkaushik.safeHome.model.SpecificRequest;
import com.pkaushik.safeHome.model.enumerations.RequestStatus;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.User;
import com.pkaushik.safeHome.model.UserRole;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SafeHomeController {
	
//Modifier Methods

//CRUD Request
	
/**
 * A request is created by the student for a walker. 
 * For MVP, assume all times are timestamped and are immediate.
 * @param mcgillID
 * @param pickupLatitude
 * @param pickupLongitude
 * @param destinationLatitude
 * @param destinationLongitude
 */
public static void createSpecificRequest(int mcgillID, double pickupLatitude, double pickupLongitude,
double destinationLatitude, double destinationLongitude) {
	
	UserRole role = SafeHomeApplication.getLoggedInUsersMap().get(mcgillID);

	//Only student can create a request.
	if(role instanceof Walker) throw new IllegalArgumentException("A Walker can not create a pickup request"); 
	
	Student student = (Student) role; 
	SpecificRequest specificRequest = null; 

	//creates locations here instead of making extra call to model to create locations. 
	Location pickupLocation = null, destinationLocation = null; 
	
	try{
	pickupLocation = new Location(pickupLatitude, pickupLongitude); 
	}
	catch(Exception e){
		throw new IllegalArgumentException("Exception creating a pickup location"); 
	}

	try{
		destinationLocation = new Location(destinationLatitude, destinationLongitude); 
	}
	catch(Exception e){
		throw new IllegalArgumentException("Error setting your destination"); 
	}

	if(pickupLocation != null && destinationLocation != null){
	try{
		specificRequest = new SpecificRequest(student, pickupLocation, destinationLocation); 
	}
	catch(Exception e){
		throw new IllegalArgumentException(e.getMessage());
	}
	}

	if(specificRequest != null){
		student.setRequest(specificRequest);
		SafeHomeApplication.addNewRequest(specificRequest, 
				new ArrayList<Location>(Arrays.asList(pickupLocation, destinationLocation)));
	}
}

public static void changeRequestDetails(int studentid) {
	SpecificRequest requestMade = null; 
	if(requestMade == null){
		throw new RuntimeException("Can't modify uncreated request.");
	}

}

public static void cancelRequest(int studentID) {
	//get request made by student
	//cancel it
	//remove its open assignment
	//if walker is assigned to it, remove walker from it. 
	
	Student studentRole = (Student) (User.getUser(studentID).getRoles().stream()
																.filter((x) -> x instanceof Student)
																.findAny()
																.orElse(null));

	SpecificRequest req = studentRole.getRequest(); 
	req.setRequestStatus(RequestStatus.CREATED);
	studentRole.setRequest(null);

	//get assignment mapped to this request. 
	Assignment assignment = studentRole.getRequest().getAssignment();
	assignment.getWalker().setCurrentAssignment(null);
	assignment.getWalker().setStatus(WalkerStatus.LOGGED_IN);
	assignment.deleteAssignment();
}

public static void selectWalker(int studentID, int walkerID) {

	//check if student has made a request. 
	User user = User.getUser(studentID); 
	Student studentRole = (Student) user.getRoles().stream().filter((x) -> (x instanceof Student)).findAny().orElse(null); 
	if(studentRole == null) throw new RuntimeException("The student does not exist");
	
	if(studentRole.getRequest() == null) throw new IllegalAccessError("A request has to be created before a walker is selected");

	//front end they have selected a logged in walker, and pass it in. 
	User walkerUser = User.getUser(walkerID); 
	Walker walkerRole = (Walker) (walkerUser.getRoles().stream().filter((x) -> (x instanceof Walker))).findAny().orElse(null);
	if(walkerRole == null) throw new RuntimeException("The walker does not exist"); 
	
	UUID assignmentID = UUID.randomUUID(); 
	Assignment walkerAssignment = new Assignment(assignmentID, studentRole.getRequest(), walkerRole); 
	//walker has to accept or deny it. 
	SafeHomeApplication.addAssignmentToMap(assignmentID, walkerAssignment);
	studentRole.getRequest().setAssignment(walkerAssignment); 
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
