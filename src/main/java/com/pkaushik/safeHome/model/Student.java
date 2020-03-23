package com.pkaushik.safeHome.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pkaushik.safeHome.SafeHomeApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name="student")
public class Student extends UserRole {

	@Id
	@Column(name="student_id")
	private int studentID;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="student_request_fk")
	@JsonManagedReference
	private SpecificRequest request; 

	@Transient
	private List<SpecificRequest> pastRequests;
	
	Student(){super();}

	//constructor to map mcgill id as primary key in persistence
	public Student(int studentID){
		pastRequests = new ArrayList<SpecificRequest>(); 
		this.studentID = studentID; 
	}

	/**
	 * @param mcgillID
	 * @return
	 */
	public static UserRole getRole(int mcgillID){
		return SafeHomeApplication.getLoggedInUsersMap().get(mcgillID);
	}
}
