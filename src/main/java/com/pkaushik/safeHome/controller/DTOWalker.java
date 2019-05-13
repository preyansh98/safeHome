package com.pkaushik.safeHome.controller;

public class DTOWalker{

    private int rating; 
	private boolean isWalksafe; 
    private String startDateTime; 
	private String endDateTime; 
	private boolean hasSchedule; 

	//Constructor for Walkers with schedules
    public DTOWalker(int rating, boolean isWalksafe, String startDateTime, String endDateTime, boolean hasSchedule){
        this.rating = rating; 
        this.isWalksafe = isWalksafe; 
        this.startDateTime = startDateTime; 
		this.endDateTime = endDateTime; 
		this.hasSchedule = hasSchedule; 
	}
	
	//Constructor for Walkers without schedules
	public DTOWalker(int rating, boolean isWalksafe, boolean hasSchedule){
        this.rating = rating; 
        this.isWalksafe = isWalksafe; 
		this.hasSchedule = hasSchedule; 
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
}