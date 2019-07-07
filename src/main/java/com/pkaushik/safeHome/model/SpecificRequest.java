package com.pkaushik.safeHome.model;

import java.util.UUID;

import javax.persistence.*;

import com.pkaushik.safeHome.model.enumerations.RequestStatus;

@Entity
@Table(name = "request")
public class SpecificRequest {

    @Id
    @GeneratedValue
    @Column(name ="request_id")
    private Long request_id;

    @Transient
    Student student;
    @Transient
    Location pickupLocation;
    @Transient
    DateTime pickupTime;
    @Transient
    Location destination;
    @Transient
    Walker walker;
    @Transient
    RequestStatus requestStatus;
    @Transient
    Assignment assignment; 

    SpecificRequest(){}
    
    public SpecificRequest(Student student, Location pickupLocation, Location destination){
        this.requestStatus = RequestStatus.CREATED; 
        this.student = student; 
        this.pickupLocation = pickupLocation; 
        this.destination = destination;
    }
    
    public SpecificRequest(Student student, double pickupLatitude, double pickupLongitude,   
    double destinationLatitude, double destinationLongitude){
        this.requestStatus = RequestStatus.CREATED; 
        this.student = student; 
        this.pickupLocation = new Location(pickupLatitude, pickupLongitude); 
        this.destination = new Location(destinationLatitude, destinationLongitude);
    }
    
    
    public Student getStudent(){
        return student; 
    }
    
    public void setStudent(Student student){
        this.student = student; 
    }
    
    public Location getPickupLocation(){
        return pickupLocation;
    }
    
    public void setPickupLocation(Location pickupLocation){
        this.pickupLocation = pickupLocation;
    }
    
    public Location getDestinationLocation(){
        return destination;
    }
    
    public void setDestinationLocation(Location destination){
        this.destination = destination;
    }
    
    public void setWalker(Walker walker){
        this.walker = walker; 
    }
    
    public Walker getWalker(){
        return walker; 
    }
    
    public void setRequestStatus(RequestStatus status){
        this.requestStatus = status; 
    }
    
    public RequestStatus getRequestStatus(){
        return requestStatus; 
    }
    
    
    public Assignment getAssignment() {
        return this.assignment;
    }
    
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
}
