package com.pkaushik.safeHome.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class User {
	
	//Attributes
	/**
	 * Phone number for the User
	 */
	private BigInteger phoneNo; 

	/**
	 * McGill ID of User
	 */
	private int mcgillID; 
	
	/**
	 * The maximum number of roles a user can have (i.e. student and walker)
	 */
	private static final int maxNumberOfRoles = 2; 

	//Associations
	/**
	 * A list of the user roles that the current user has
	 */
	private List<UserRole> roles; 

	private static HashMap<Integer, User> userMap = new HashMap<Integer, User>(); 
	private SafeHome safeHome = SafeHome.getSafeHomeInstance(); 
	
	/**
	 * Defining a constructor that requires user to have phoneNo and mcGillID
	 */
	public User(BigInteger phoneNo, int mcgillID, SafeHome safeHome) {
		
		//validation checks for phoneNo
		 
		String phoneDigits = (phoneNo.toString()); 
		if(phoneDigits.length() != 10) {
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
		
		if(idDigits.length != 9) {
			throw new IllegalArgumentException("Please enter a valid McGill ID");
		}
		
		this.mcgillID = mcgillID; 
		this.phoneNo = phoneNo; 

		userMap.put(mcgillID, this); 
		roles = new ArrayList<UserRole>(); 
		this.setRoles(roles);
		this.setSafeHome(safeHome);
	}

	/**
	 * @return the phoneNo
	 */
	public BigInteger getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(BigInteger phoneNo) {
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
		List<UserRole> newRoles = Collections.unmodifiableList(roles);
		return newRoles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public boolean addRole(UserRole role){
		boolean result; 
		List<UserRole> UserRoles = this.roles; 
		//validations:
		//1. can't add role if already exists
		if(UserRoles.contains(role)) return false; 
		//2. can't be over maximum number of roles
		if(UserRoles.size() >= maxNumberOfRoles) return false; 

		UserRoles.add(role);
		setRoles(UserRoles); 
		result = true; 
		return result; 
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
	public void setSafeHome(SafeHome safeHomeInput) {
		SafeHome currSafeHome = safeHome; 
		if(currSafeHome != null && !currSafeHome.equals(safeHomeInput)){
			currSafeHome.removeUser(this); 
		}
		safeHome = safeHomeInput; 
		safeHome.addUser(this); 
	}

	public static HashMap<Integer, User> getUserMap(){
		return userMap; 
	}

	public static void setUserMap(HashMap<Integer, User> userMapInput){
		userMap = userMapInput; 
	}

	public static User getUser(int mcgillID) {
		HashMap<Integer, User> map = getUserMap(); 
		User userFound = map.get(mcgillID); 

		return userFound; 
	}

}
