package com.pkaushik.safeHome.model;

import java.util.HashMap;
import java.util.List;

import javax.persistence.*;

import com.pkaushik.safeHome.model.enumerations.WalkerStatus;

@Entity
@Table(name = "Walker")
public class Walker extends UserRole {

	//for persistence
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int walker_id; 

	//Attributes
	@Column(name = "rating")
	private int rating; 

	@Column(name = "isWalksafe")
	private boolean isWalksafe; 

	@Column(name = "hasSchedule")
	private boolean hasSchedule = false; 

	private Schedule schedule; 
	private WalkerStatus status; 
	private Assignment currentAssignment; 

	private SafeHome safeHome = SafeHome.getSafeHomeInstance(); 
	
	private HashMap<Integer, Walker> walkerMap = new HashMap<Integer, Walker>();
	
	public Walker(SafeHome safeHome, boolean isWalksafe) {
		super(safeHome);
		this.setSafeHome(safeHome);
		//always create a walker with an empty schedule. 
		rating = 0; 
		this.isWalksafe = isWalksafe; 
		status = WalkerStatus.INACTIVE;
	}
	
	public static Walker getWalker(int mcgillID){
		User userWithID = User.getUser(mcgillID);
		UserRole walkerRole = null; 
		List<UserRole> userRoles = userWithID.getRoles();
		for(UserRole role : userRoles){
			if(role instanceof Walker){
				walkerRole = role; 
				break; 
			}
		}
		if(walkerRole == null) throw new IllegalAccessError("Walker with ID does not exist"); 
		else{
			return (Walker) walkerRole; 
		}
	}
	
	public HashMap<Integer, Walker> getWalkerMap() {
		return walkerMap;
	}
	
	public void setWalkerMap(HashMap<Integer, Walker> walkerMap) {
		this.walkerMap = walkerMap;
	}
	
	
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	/**
	 * @return the isWalksafe
	 */
	public boolean isWalksafe() {
		return isWalksafe;
	}
	
	/**
	 * @param isWalksafe the isWalksafe to set
	 */
	public void setWalksafe(boolean isWalksafe) {
		this.isWalksafe = isWalksafe;
	}
	
	/**
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		if(schedule == null && hasSchedule == false){
			throw new IllegalAccessError("This walker does not have a schedule. Please create one before trying to access it.");
		}
		else{
			hasSchedule = true; 
			return schedule;
		}
	}
	
	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(Schedule schedule) {
		hasSchedule = true; 
		this.schedule = schedule;
	}
	
	@Override
	public void setSafeHome(SafeHome safeHomeInput){
		SafeHome currSafeHome = safeHome; 
		if(currSafeHome != null && !currSafeHome.equals(safeHomeInput)){
			currSafeHome.removeWalker(this); 
		}
		safeHome = safeHomeInput; 
		safeHome.addWalker(this); 
	}
	
	public boolean hasSchedule() {
		return this.hasSchedule; 
	}
	
	/**
	 * @return the status
	 */
	public WalkerStatus getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(WalkerStatus status) {
		this.status = status;
	}
	
	public Assignment getCurrentAssignment() {
		return this.currentAssignment;
	}
	
	public void setCurrentAssignment(Assignment currentAssignment) {
		this.currentAssignment = currentAssignment;
	}
	
	
}

