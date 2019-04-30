package com.pkaushik.safeHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.UserRole;

@SpringBootApplication
public class SafeHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeHomeApplication.class, args);
	}

	private static SafeHome safeHome; 
	private static UserRole currentUserRole; 
	
}
