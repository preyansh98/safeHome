package com.pkaushik.safeHome.controller;
import com.pkaushik.safeHome.model.Walker.walkerStatus;
public class DTOWalker{

    private int rating; 
	private boolean isWalksafe; 
    private String startDateTime; 
	private String endDateTime; 
	private boolean hasSchedule; 
	private walkerStatus status;

	
	
	//Constructor for Walkers with schedules
	public DTOWalker(int rating, boolean isWalksafe, String startDateTime, String endDateTime, 
	boolean hasSchedule, walkerStatus walkerStatus){
		this.rating = rating; 
        this.isWalksafe = isWalksafe; 
        this.startDateTime = startDateTime; 
		this.endDateTime = endDateTime; 
		this.setHasSchedule(hasSchedule); 
		this.status = walkerStatus;
	}
	
	//Constructor for Walkers without schedules
	public DTOWalker(int rating, boolean isWalksafe, boolean hasSchedule){
		this.rating = rating; 
        this.isWalksafe = isWalksafe; 
		this.setHasSchedule(hasSchedule); 
    }
	
    public int getRating() {
		return this.rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public boolean getIsWalksafe() {
		return this.isWalksafe;
	}
	
	public void setIsWalksafe(boolean isWalksafe) {
		this.isWalksafe = isWalksafe;
	}
	
	public String getStartDateTime() {
		return this.startDateTime;
	}
	
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
    
	public String getEndDateTime() {
		return this.endDateTime;
	}
    
    public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
    }
	
	
	/**
	 * @return the hasSchedule
	 */
	public boolean isHasSchedule() {
		return hasSchedule;
	}
	
	/**
	 * @param hasSchedule the hasSchedule to set
	 */
	public void setHasSchedule(boolean hasSchedule) {
		this.hasSchedule = hasSchedule;
	}

	public walkerStatus getStatus() {
		return this.status;
	}
		
	public void setStatus(walkerStatus status) {
		this.status = status;
	}
}