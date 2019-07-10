package com.pkaushik.safeHome.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.*;

import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_ID;
import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_PHONE;
import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_NO_OF_ROLES;

@Entity
@Table(name ="sh_user")
public class SafeHomeUser {
	
	//Attributes
	/**
	 * Phone number for the User
	 */
	@Column(name = "sh_user_phone")
	private BigInteger phoneNo; 

	/**
	 * McGill ID of User
	 */
	@Id
	@Column(name = "sh_user_id")
	private int mcgillID; 

	//Associations
	/**
	 * A list of the user roles that the current user has
	 */
	@Transient
	private List<UserRole> roles; 

	@Transient
	private static HashMap<Integer, SafeHomeUser> userMap = new HashMap<Integer, SafeHomeUser>();

	SafeHomeUser(){}

	/**
	 * Defining a constructor that requires user to have phoneNo and mcGillID
	 */
	public SafeHomeUser(BigInteger phoneNo, int mcgillID) {
		this.mcgillID = mcgillID; 
		this.phoneNo = phoneNo;

		userMap.put(mcgillID, this); 
		roles = new ArrayList<UserRole>(); 
		this.setRoles(roles);
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
