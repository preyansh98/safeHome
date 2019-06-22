package com.pkaushik.safeHome.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id; 	

import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_ID;
import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_PHONE;
import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_NO_OF_ROLES;


@Entity
@Table(name = "safehomeuser")
public class SafeHomeUser {
	
	//Attributes
	/**
	 * Phone number for the User
	 */
	private BigInteger phoneNo; 

	/**
	 * McGill ID of User
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "_id")
	private int mcgillID; 
	


	//Associations
	/**
	 * A list of the user roles that the current user has
	 */
	private List<UserRole> roles; 

	private static HashMap<Integer, SafeHomeUser> userMap = new HashMap<Integer, SafeHomeUser>(); 
	private SafeHome safeHome = SafeHome.getSafeHomeInstance(); 

	/**
	 * Defining a constructor that requires user to have phoneNo and mcGillID
	 */
	public SafeHomeUser(BigInteger phoneNo, int mcgillID, SafeHome safeHome) {
		
		//validation checks for phoneNo
		 
		String phoneDigits = (phoneNo.toString()); 
		if(phoneDigits.length() != MAX_DIGITS_FOR_PHONE) {
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
		
		if(idDigits.length != MAX_DIGITS_FOR_ID) {
			throw new IllegalArgumentException("Please enter a valid McGill ID");
		}
		
		this.mcgillID = mcgillID; 
		this.phoneNo = phoneNo; 

		userMap.put(mcgillID, this); 
		roles = new ArrayList<UserRole>(); 
		this.setRoles(roles);
		this.setSafeHome(SafeHome.getSafeHomeInstance());
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
		if(UserRoles.size() >= MAX_NO_OF_ROLES) return false; 

		UserRoles.add(role);
		setRoles(UserRoles);
		return true;
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

	public static HashMap<Integer, SafeHomeUser> getUserMap(){
		return userMap; 
	}

	public static void setUserMap(HashMap<Integer, SafeHomeUser> userMapInput){
		userMap = userMapInput; 
	}

	public static SafeHomeUser getUser(int mcgillID) {
		HashMap<Integer, SafeHomeUser> map = getUserMap(); 
		SafeHomeUser userFound = map.get(mcgillID); 

		return userFound; 
	}

}
