package com.pkaushik.safeHome.model;

public class Schedule {

	private DateTime startDate; 
	private DateTime endDate; 
	
	//Association
	private Walker walker; 
	
	@Deprecated
	/**
	 * @deprecated: The schedule startDates and Times have to be set manually
	 * Replaced by overloaded constructor that takes in startDate ints, endDate ints,
	 * startTime ints, EndTime ints.
	 */
	public Schedule(DateTime startDate, DateTime endDate) {
		this.startDate = startDate; 
		this.endDate = endDate; 
	}	
	
	public Schedule(int startDay, int startMonth, int startYear, 
					int endDay, int endMonth, int endYear,
					int startHour, int startMin, int endHour, int endMin)
			{
				startDate = new DateTime(); 
				endDate = new DateTime(); 
				//Configure for API
				//java.util.date uses Jan as 00, Date is years since 1900
				setStartDate(startDay, startMonth - 1, startYear-1900); 
				setEndDate(endDay, endMonth - 1, endYear-1900);
				setStartTime(startHour, startMin);
				setEndTime(endHour, endMin);
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
	
	public void setStartDay(int date) {
		startDate.currDate.setDate(date);
	}
	public void setStartMonth(int month) {
		startDate.currDate.setMonth(month);
	}
	public void setStartYear(int year) {
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

	public void setEndDay(int date) {
		endDate.currDate.setDate(date);
	}
	public void setEndMonth(int month) {
		endDate.currDate.setMonth(month);
	}
	public void setEndYear(int year) {
		endDate.currDate.setYear(year);
	}
	
	/**
	 * set start time (date, month, year) all in ints
	 * @param hours
	 * @param minutes
	 */
	@SuppressWarnings("deprecation")
	public void setStartTime(int hours, int minutes) {
		startDate.currDate.setHours(hours);
		startDate.currDate.setMinutes(minutes);
		startDate.currDate.setSeconds(0);
	}
	
	public void setStartHour(int startHour) {
		startDate.currDate.setHours(startHour);
	}
	
	public void setStartMin(int startMin) {
		startDate.currDate.setMinutes(startMin);
	}

	/**
	 * set end time (date, month, year) all in ints
	 * @param hours
	 * @param minutes
	 */
	@SuppressWarnings("deprecation")
	public void setEndTime(int hours, int minutes) {
		endDate.currDate.setHours(hours);
		endDate.currDate.setMinutes(minutes);
		endDate.currDate.setSeconds(0);
	}

	public void setEndHour(int endHour) {
		endDate.currDate.setHours(endHour);
	}
	
	public void setEndMin(int endMin) {
		endDate.currDate.setMinutes(endMin);
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
