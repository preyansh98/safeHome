package com.pkaushik.safeHome.model;

import java.util.UUID;

import com.pkaushik.safeHome.SafeHomeApplication;

import javax.persistence.*;

/**
 * This class links the walker to a specific request submitted by a student
 * 
 * @author Preyansh
 *
 */

@Entity
@Table(name = "assignment")
public class Assignment {
	
	//Associations
	@Transient
	private Walker walker;
	@Transient
	private SpecificRequest request;

	@Id
	@Column(name = "assignment_id")
	private UUID assignmentID;

	@Column(name = "assignment_accepted")
	boolean isAccepted;

	Assignment(){}

	public Assignment(UUID assignmentID, SpecificRequest request, Walker walker) {
		this.isAccepted = false; 
		this.assignmentID = assignmentID; 
		this.walker = walker; 
		this.request = request; 
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
