package com.pkaushik.safeHome.model;

public class Student extends UserRole{

	private SafeHome safeHome = SafeHome.getSafeHomeInstance(); 
	public Student(SafeHome safehome) {
		super(safehome);
		this.setSafeHome(safeHome);
	}
	
	private SpecificRequest request; 

	@Override
	public void setSafeHome(SafeHome safeHomeInput){
		SafeHome currSafeHome = safeHome; 
		if(currSafeHome != null && !currSafeHome.equals(safeHomeInput)){
			currSafeHome.removeStudent(this); 
		}
		safeHome = safeHomeInput; 
		safeHome.addStudent(this); 
	}
}
