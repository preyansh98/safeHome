package com.pkaushik.safeHome.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="walker")
public class Walker extends UserRole {

	//Attributes

	@Id
	@Column(name="walker_id")
	private int walkerid;

	@Column(name="walker_rating")
	private double rating;

	@Column(name = "walker_walksafe")
	private boolean isWalksafe;

	@Transient
	private Schedule schedule;

	@Enumerated(EnumType.STRING)
	@Column(name = "walker_status")
	private WalkerStatus status;

	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="walker_assignment_fk")
	@JsonManagedReference
	private Assignment currentAssignment;

	Walker(){super();}

	public Walker(int walkerid, boolean isWalksafe) {
		rating = 0;
		this.isWalksafe = isWalksafe;
		status = WalkerStatus.INACTIVE;
		this.walkerid=walkerid;
	}

	public static UserRole getRole(int mcgillID){
		return SafeHomeApplication.getLoggedInUsersMap().get(mcgillID);
	}
}

