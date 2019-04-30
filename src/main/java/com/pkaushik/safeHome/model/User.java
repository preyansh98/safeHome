package com.pkaushik.safeHome.model;

import java.util.List;

public class User {
	
	//Attributes
	/**
	 * Phone number for the User
	 */
	private int phoneNo; 
	/**
	 * McGill ID of User
	 */
	private int mcgillID; 
	
	//Associations
	/**
	 * A list of the user roles that the current user has
	 */
	private List<UserRole> roles; 
	private SafeHome safeHome; 
	
	/**
	 * Defining a constructor that requires user to have phoneNo and mcGillID
	 */
	public User(int phoneNo, int mcgillID) {
		
		//validation checks for phoneNo
		 
		char[] phoneDigits = ("" + phoneNo).toCharArray(); 
		if(phoneDigits.length >10) {
			throw new IllegalArgumentException("Please enter a valid phone number"); 
		}
		
		char[] idDigits = ("" + mcgillID).toCharArray();
		if(
				(Character.getNumericValue(idDigits[0]) == 2) 
				&& (Character.getNumericValue(idDigits[1]) == 6) &&
				(Character.getNumericValue(idDigits[2]) == 0)
		) {
			//mcgill ID is okay
		}
		else {
			throw new IllegalArgumentException("McGill ID should start with '260'"); 
		}
		
		if(idDigits.length > 10) {
			throw new IllegalArgumentException("Please enter a valid McGill ID");
		}
		
		this.mcgillID = mcgillID; 
		this.phoneNo = phoneNo; 
	}

	/**
	 * @return the phoneNo
	 */
	public int getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the mcgillID
	 */
	public int getMcgillID() {
		return mcgillID;
	}

	/**
	 * @param mcgillID the mcgillID to set
	 */
	public void setMcgillID(int mcgillID) {
		this.mcgillID = mcgillID;
	}

	/**
	 * @return the roles
	 */
	public List<UserRole> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
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
}
