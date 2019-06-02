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
	private Student student; 

	boolean isAccepted = false; 

	/**
	 * Whenever an assignment is created, this class pings the walker to accept or refuse assignment. 
	 */
	public Assignment(Student student, SpecificRequest request, Walker walker) {
		this.student = student; 
		this.walker = walker; 
		this.request = request; 

		queryWalker(walker);
	}
	
	private void queryWalker(Walker walker){
		//walker will choose to accept or not. 
		//send this req to front end. 
		//controller method for walker to accept request. 
	}
	
	
}
