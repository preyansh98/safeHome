package com.pkaushik.safeHome.model;

public abstract class UserRole {

	//Attributes
	private String password; 
	
	//Associations
	private SafeHome safeHome; 
	
	//Constructor
	public UserRole(String password, SafeHome safeHome) {
		
		//password validation
		this.password = password;
		this.safeHome = safeHome; 
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the safeHome
	 */	
	public SafeHome getSafeHome() {
		return safeHome;
	}

	/**
	 * @param safeHome the safeHome to set
	 */
	public void setSafeHome(SafeHome safeHome) {
		this.safeHome = safeHome;
	}
	
	
	public void delete() {
		SafeHome tmp = this.safeHome;
		this.safeHome = null; 
		if(tmp != null) {
			tmp.removeRole(this);
		}
	}
	
}
