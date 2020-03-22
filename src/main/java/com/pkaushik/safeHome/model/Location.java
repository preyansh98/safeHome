package com.pkaushik.safeHome.model;

import lombok.Data;

import javax.persistence.*;

@Data
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
}
