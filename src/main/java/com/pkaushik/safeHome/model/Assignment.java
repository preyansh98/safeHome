package com.pkaushik.safeHome.model;

import java.util.UUID;

import com.pkaushik.safeHome.SafeHomeApplication;

/**
 * This class links the walker to a specific request submitted by a student
 * 
 * @author Preyansh
 *
 */
public class Assignment {
	
	//Associations
	private Walker walker; 
	private SpecificRequest request; 
	private UUID assignmentID; 
	boolean isAccepted; 

	/**
	 * Whenever an assignment is created, this class pings the walker to accept or refuse assignment. 
	 */
	public Assignment(UUID assignmentID, SpecificRequest request, Walker walker) {
		this.isAccepted = false; 
		this.assignmentID = assignmentID; 
		this.walker = walker; 
		this.request = request; 
		queryWalker(walker);
	}
	
	private void queryWalker(Walker walker){
		//walker will choose to accept or not. 
		//send this req to front end. 
		//controller method for walker to accept request. 
		
		//should display location, time. 
		
	}

	public void deleteAssignment(){
		SafeHomeApplication.removeAssignmentFromMap(this.assignmentID);
		this.isAccepted = false; 
		this.walker = null; 
		this.request = null; 
	}
	
	public Walker getWalker() {
		return this.walker;
	}
	
	public void setWalker(Walker walker) {
		this.walker = walker;
	}
	
	public SpecificRequest getRequest() {
		return this.request;
	}
	
	public void setRequest(SpecificRequest request) {
		this.request = request;
	}
	
	public UUID getAssignmentID() {
		return this.assignmentID;
	}
	
	public void setAssignmentID(UUID assignmentID) {
		this.assignmentID = assignmentID;
	}
	
	public boolean hasAccepted() {
		return this.isAccepted; 
	}
	
	public void isAccepted(boolean isAccepted ) {
		this.isAccepted = isAccepted;
	}
}
