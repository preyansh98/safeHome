package com.pkaushik.safeHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.model.Walker.walkerStatus;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SpringBootApplication
public class SafeHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeHomeApplication.class, args);
	}

	//map ensures that with same id, user can't be logged in twice. no duplicate keys
	private static Map<Integer, UserRole> loggedInUsers = new HashMap<Integer, UserRole>();
	
	//map for all the current requests that are not fulfilled, and their location data
	private static Map<SpecificRequest, List<Location>> currentRequestsMap = new HashMap<SpecificRequest, List<Location>>(); 

	private static UserRole currentUserRole;
	private static SpecificRequest currentRequest;
	private static SafeHome safeHome = getSafeHome();

	public static SafeHome getSafeHome(){
		return SafeHome.getSafeHomeInstance();
	}

	//change user role to admin or not.
	public static UserRole getCurrentUserRole(){
		return currentUserRole;
	}

	//change this to only for admin or not.
	public static void setCurrentUserRole(UserRole role){
		SafeHomeApplication.currentUserRole = role;
	}

	public static SpecificRequest getCurrentRequest(){
		return currentRequest;
	}

	public static void setCurrentRequest(SpecificRequest currentRequest){
		SafeHomeApplication.currentRequest = currentRequest;
	}

	public static void resetAll(){
		if(safeHome!=null){
			safeHome.delete();
		}
		UserRole currentRole = getCurrentUserRole();
		if(currentRole != null) {
			if(currentRole instanceof Walker) {
				((Walker) currentRole).setStatus(walkerStatus.INACTIVE);
			}
		}
		setCurrentRequest(null);
		setCurrentUserRole(null);
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

	//TODO: change location, etc. 
}