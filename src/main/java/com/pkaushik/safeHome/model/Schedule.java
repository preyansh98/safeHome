package com.pkaushik.safeHome.model;

public class Schedule {

	private DateTime startDate; 
	private DateTime endDate; 
	
	//Association
	private Walker walker; 
	
	public Schedule(Walker walker) {
		this.walker = walker; 
	}	
	
	/**
	 * @return the startDate
	 */
	public DateTime getStartDate() {
		return startDate;
	}
	
	/**
	 * set start date (date, month, year) all in ints
	 * @param date
	 * @param month
	 * @param year
	 */
	@SuppressWarnings("deprecation")
	public void setStartDate(int date, int month, int year) {
		startDate.currDate.setDate(date);
		startDate.currDate.setMonth(month);
		startDate.currDate.setYear(year);
	}
	
	/**
	 * @return the endDate
	 */
	public DateTime getEndDate() {
		return endDate;
	}

	@SuppressWarnings("deprecation")
	public void setEndDate(int date, int month, int year) {
		endDate.currDate.setDate(date);
		endDate.currDate.setMonth(month);
		endDate.currDate.setYear(year);
	}

	/**
	 * @return the walker
	 */
	public Walker getWalker() {
		return walker;
	}

	/**
	 * @param walker the walker to set
	 */
	public void setWalker(Walker walker) {
		this.walker = walker;
	}

	
}
