package com.pkaushik.safeHome.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	
	DateFormat dateForm; 
	Date currDate; 
	
	public DateTime() {
	dateForm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	currDate = new Date();
	}
	
	public String getDateTime() {
		return (dateForm.format(currDate)); 
	}
	
	public String getOnlyDate() {
		return (dateForm.format(currDate).substring(0, 10).trim());
	}
	
	public String getOnlyTime() {
		return (dateForm.format(currDate).substring(11, 18)); 
	}
	
	public String getMonth() {
		String dtf = dateForm.format(currDate).substring(3, 4);
		return dtf; 
	}
	
	public String getSeconds() {
		return (dateForm.format(currDate).substring(17, 18)); 
	}
	
	public String getMins() {
		return (dateForm.format(currDate).substring(15, 16)); 
	}
	
}
