package com.pkaushik.safeHome.model;

/**
 * This class links the walker to a specific request submitted by a student
 * @author Preyansh
 *
 */
public class Assignment {
	
	//Associations
	private Walker walker; 
	private SpecificRequest request; 
	
	public Assignment(Walker walker, SpecificRequest request) {
		this.walker = walker; 
		this.request = request; 
	}
	
	
}
