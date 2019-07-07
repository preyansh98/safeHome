package com.pkaushik.safeHome.model;

import javax.persistence.*;

@Entity
@Table(name="location")
public class Location {

	@Id
	@GeneratedValue
	@Column(name="location_id")
	private int location_id;

	@Column(name="location_lat")
	private double latitude;

	@Column(name="location_long")
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

}
