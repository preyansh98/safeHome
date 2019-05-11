package com.pkaushik.safeHome.model;

public abstract class UserRole {
	
	//Associations
	private SafeHome safeHome; 
	
	//Constructor
	public UserRole(SafeHome safeHome) {
		this.safeHome = safeHome; 
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
