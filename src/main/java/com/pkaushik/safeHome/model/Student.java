package com.pkaushik.safeHome.model;

public class Student extends UserRole{

	private SafeHome safeHome = SafeHome.getSafeHomeInstance(); 
	private SpecificRequest request; 
	
	
	public Student(SafeHome safehome) {
		super(safehome);
		this.setSafeHome(safeHome);
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
}
