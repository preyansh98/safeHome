package com.pkaushik.safeHome;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.Location;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.SpecificRequest;
import com.pkaushik.safeHome.model.UserRole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafeHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeHomeApplication.class, args);
	}

	//map ensures that with same id, user can't be logged in twice. no duplicate keys
	private static Map<Integer, UserRole> loggedInUsers = new HashMap<Integer, UserRole>();
	
	//map for all the current requests that are not fulfilled, and their location data
	private static Map<SpecificRequest, List<Location>> currentRequestsMap = new HashMap<SpecificRequest, List<Location>>(); 

	//map for open assignments that haven't been accepted yet. 
	private static Map<UUID, Assignment> openAssignmentsMap = new HashMap<UUID, Assignment>(); 

	private static SafeHome safeHome = getSafeHome();
	
	public static SafeHome getSafeHome(){
		return SafeHome.getSafeHomeInstance();
	}
	
	public static void resetAll(){
		if(safeHome!=null){
			safeHome.delete();
		}
		loggedInUsers.clear(); 
		currentRequestsMap.clear(); 
		openAssignmentsMap.clear(); 
		System.gc();
	}
	
	//make this unmodifiable
	public static Map<Integer, UserRole> getLoggedInUsersMap() {
		return Collections.unmodifiableMap(loggedInUsers);
	}

	//should never be used
	//TODO: only run when in dev mode.
	public static void setLoggedInUsersMap(Map<Integer, UserRole> userMap) {
		System.out.println("should only run in dev");
		SafeHomeApplication.loggedInUsers = userMap;
	}
	
	//implementation dependent
	public static void logInUser(int mcgillID, UserRole role){
		SafeHomeApplication.loggedInUsers.put(mcgillID, role);
	}

	public static void logOutUser(int mcgillID){
		if(SafeHomeApplication.loggedInUsers == null) throw new IllegalStateException("Can't log out user that hasn't logged in");
		SafeHomeApplication.loggedInUsers.remove(mcgillID);
	}

	public static Map<SpecificRequest, List<Location>> getCurrentRequestsMap(){
		return currentRequestsMap; 
	}
	
	public static void setCurrentRequestsMap(Map<SpecificRequest, List<Location>> currentRequestsMap){
		SafeHomeApplication.currentRequestsMap = currentRequestsMap; 
	}

	//implementation dependent operations
	public static void addNewRequest(SpecificRequest request, List<Location> locations){
		currentRequestsMap.put(request, locations); 
	}

	public static void removeRequest(SpecificRequest request, List<Location> locations){
		currentRequestsMap.remove(request); 
	}

	public static void addAssignmentToMap(UUID uuid, Assignment assignment){
		(openAssignmentsMap).put(uuid, assignment);
	}

	public static Map<UUID, Assignment> getOpenAssignmentsMap(){
		return Collections.unmodifiableMap(openAssignmentsMap); 
	}

	public static void removeAssignmentFromMap(UUID uuid){
		openAssignmentsMap.remove(uuid); 
	}

	public static void setOpenAssignmentsMap(Map<UUID, Assignment> openAssignmentsMap){
		SafeHomeApplication.openAssignmentsMap = openAssignmentsMap; 
	}

}