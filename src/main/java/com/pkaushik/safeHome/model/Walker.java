package com.pkaushik.safeHome.model;


public class Walker extends UserRole{

	//Attributes
	private int rating; 
	private boolean isWalksafe; 
	private Schedule schedule; 
	private SafeHome safeHome = SafeHome.getSafeHomeInstance(); 
	
	public Walker(SafeHome safeHome) {
		super(safeHome);
		this.setSafeHome(safeHome);
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
		return schedule;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(Schedule schedule) {
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
}
