package com.pkaushik.safeHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pkaushik.safeHome.model.*; 

@SpringBootApplication
public class SafeHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeHomeApplication.class, args);
	}

	private static UserRole currentUserRole;
	private static SpecificRequest currentRequest; 
	private static SafeHome safeHome = getSafeHome(); 
	
	public static SafeHome getSafeHome(){
		return SafeHome.getSafeHomeInstance(); 
	}

	public static UserRole getCurrentUserRole(){
		return currentUserRole; 
	}

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
		setCurrentRequest(null);
		setCurrentUserRole(null);
		System.gc(); 
	}

	
}