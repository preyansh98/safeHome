package com.pkaushik.safeHome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.ArrayList; 

@Entity
public class Student extends UserRole {
	
	@Id
	@Column(name = "mcgillid")
	private int studentID;

	public int getStudentID() {
		return this.studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
  //configured to be same as mcgillid

	private SafeHome safeHome = SafeHome.getSafeHomeInstance(); 
	
	private SpecificRequest request; 


	private List<SpecificRequest> pastRequests; 
	
	public Student(SafeHome safehome) {
		super(safehome);
		this.setSafeHome(safeHome);
		pastRequests = new ArrayList<SpecificRequest>(); 
	}
	
	//constructor to map mcgill id as primary key in persistence
	public Student(int studentID, SafeHome safeHome){
		super(safeHome);
		this.setSafeHome(safeHome);
		pastRequests = new ArrayList<SpecificRequest>(); 
		this.studentID = studentID; 
	}

	@Override
	public void setSafeHome(SafeHome safeHomeInput){
		SafeHome currSafeHome = safeHome; 
		if(currSafeHome != null && !currSafeHome.equals(safeHomeInput)){
			currSafeHome.removeStudent(this); 
		}
		safeHome = safeHomeInput; 
		safeHome.addStudent(this); 
	}


	/**
	 * @return the request
	 */
	public SpecificRequest getRequest() {
		return request;
	}


	/**
	 * @param request the request to set
	 */
	public void setRequest(SpecificRequest request) {
		this.request = request;
	}

	public List<SpecificRequest> getPastRequests(){
		return pastRequests; 
	}

	public void setPastRequests(List<SpecificRequest> listRequests){
		this.pastRequests = listRequests; 
	}
}
