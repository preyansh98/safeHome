package com.pkaushik.safeHome.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pkaushik.safeHome.SafeHomeApplication;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * This class links the walker to a specific request submitted by a student
 * 
 * @author Preyansh
 *
 */

@Getter
@Setter
@Entity
@Table(name = "assignment")
public class Assignment {
	
	//Associations
	@OneToOne(mappedBy = "currentAssignment", cascade = CascadeType.ALL)
	@JsonBackReference
	private Walker walker;

	@Transient
	private SpecificRequest request;

	@Id
	@Column(name = "assignment_id")
	private UUID assignmentID;

	@Column(name = "assignment_accepted")
	boolean isAccepted;

	Assignment(){}

	public Assignment(UUID assignmentID, SpecificRequest request, Walker walker) {
		this.isAccepted = false;
		this.assignmentID = assignmentID;
		this.walker = walker;
		this.request = request;
	}
}
