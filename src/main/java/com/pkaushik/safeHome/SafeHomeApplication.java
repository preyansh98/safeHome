package com.pkaushik.safeHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.model.Walker.walkerStatus; 
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
public class SafeHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeHomeApplication.class, args);
	}

	
	//TODO: might change this to a hashmap with mcgillID and session id
	private static List<UserRole> loggedInUsers = new ArrayList<UserRole>(); 

	
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
	public static List<UserRole> getLoggedInUsersList() {
		return Collections.unmodifiableList(loggedInUsers);
	}
	
	//should never be used
	//TODO: only run when in dev mode. 
	public static void setLoggedInUsersList(List<UserRole> userList) {
		System.out.println("should only run in dev");
		SafeHomeApplication.loggedInUsers = userList;
	}

	public static void addUserToList(UserRole user){
		SafeHomeApplication.loggedInUsers.add(user);
	}
	
	public static void removeUserFromList(UserRole user){
		if(SafeHomeApplication.loggedInUsers == null) throw new IllegalStateException("Can't remove user that hasn't logged in");
		SafeHomeApplication.loggedInUsers.remove(user);
	}
	
}