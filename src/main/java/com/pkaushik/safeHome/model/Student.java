package com.pkaushik.safeHome.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList; 

@Entity
@Table(name="student")
public class Student extends UserRole {

	@Id
	@Column(name="student_id")
	private int studentID;

	public int getStudentID() {
		return this.studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
  //configured to be same as mcgillid

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
	 * TODO: THIS METHOD IS BROKEN! roles are transactional.
	 * @param mcgillID
	 * @return
	 */
	public static UserRole getRole(int mcgillID){

		SafeHomeUser userWithID = SafeHomeUser.getUser(mcgillID);
		UserRole studentRole = null;
		List<UserRole> userRoles = userWithID.getRoles();
		for(UserRole role : userRoles){
			if(role instanceof Student){
				studentRole = role;
				break;
			}
		}
		if(studentRole == null) throw new IllegalAccessError("Walker with ID does not exist");
		else{
			return (Student) studentRole;
		}
	}

	/**
	 * @return the request
	 */
	public SpecificRequest getRequest() {
		return request;
	}


	/**
	 * @param request the request to set
	 */
	public void setRequest(SpecificRequest request) {
		this.request = request;
	}

	public List<SpecificRequest> getPastRequests(){
		return pastRequests; 
	}

	public void setPastRequests(List<SpecificRequest> listRequests){
		this.pastRequests = listRequests; 
	}
}
