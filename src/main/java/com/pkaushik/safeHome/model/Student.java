package com.pkaushik.safeHome.model;

import java.util.List;
import java.util.ArrayList; 

public class Student extends UserRole {

	private SafeHome safeHome = SafeHome.getSafeHomeInstance(); 
	private SpecificRequest request; 
	private List<SpecificRequest> pastRequests; 
	
	public Student(SafeHome safehome) {
		super(safehome);
		this.setSafeHome(safeHome);
		pastRequests = new ArrayList<SpecificRequest>(); 
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
