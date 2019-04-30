package com.pkaushik.safeHome.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SafeHomeController {
	
@RequestMapping("/user")
public String returnIndex() {
	Instant instant = Instant.now(); 
	return instant.toString(); 
	
}
	
//Modifier Methods

public static void createSpecificRequest() {
	
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
