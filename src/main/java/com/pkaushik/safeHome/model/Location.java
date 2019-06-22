package com.pkaushik.safeHome.model;

import static com.pkaushik.safeHome.utils.APIKeys.MAPS_KEY;

public class Location {
	//we'll have to incorporate google maps api for address

	private double latitude; 
	private double longitude; 

	public Location(double latitude, double longitude){
		this.longitude = longitude; 
		this.latitude = latitude; 
	}

	public double getLatitude(){
		return latitude; 
	}

	public double getLongitude(){
		return longitude; 
	}

	public void setLatitude(double latitude){
		this.latitude = latitude; 
	}

	public void setLongitude(double longitude){
		this.longitude = longitude; 
	}

	//address lookup

	//Geocoding: 
}
