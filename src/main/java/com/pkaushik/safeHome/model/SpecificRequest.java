package com.pkaushik.safeHome.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pkaushik.safeHome.model.enumerations.RequestStatus;

@Entity
@Table(name = "request")
public class SpecificRequest {

    @Id
    @Column
    private int requestID = 1; 

    Student student;
    Location pickupLocation; 
    DateTime pickupTime; 
    Location destination;  
    Walker walker;
    RequestStatus requestStatus; 
    Assignment assignment; 

     //For Ease of Persistence
    @Column
    double pickupLat = pickupLocation.getLatitude(); 

    @Column
    double pickupLon = pickupLocation.getLongitude(); 

    @Column
    double destLat = destination.getLatitude(); 

    @Column
    double destLon = destination.getLongitude(); 
    
    
    public SpecificRequest(Student student, Location pickupLocation, Location destination){
        this.requestStatus = RequestStatus.CREATED; 
        this.student = student; 
        this.pickupLocation = pickupLocation; 
        this.destination = destination; 
        
        pickupLat = pickupLocation.getLatitude(); 
        pickupLon = pickupLocation.getLongitude(); 
        destLat = destination.getLatitude(); 
        destLon = destination.getLongitude(); 
        requestID++; 
    }
    
    public SpecificRequest(Student student, double pickupLatitude, double pickupLongitude,   
    double destinationLatitude, double destinationLongitude){
        this.requestStatus = RequestStatus.CREATED; 
        this.student = student; 
        this.pickupLocation = new Location(pickupLatitude, pickupLongitude); 
        this.destination = new Location(destinationLatitude, destinationLongitude);  

        pickupLat = pickupLocation.getLatitude(); 
        pickupLon = pickupLocation.getLongitude(); 
        destLat = destination.getLatitude(); 
        destLon = destination.getLongitude();   
        requestID++;     
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
        pickupLat = pickupLocation.getLatitude(); 
        pickupLon = pickupLocation.getLongitude(); 
    }
    
    public Location getDestinationLocation(){
        return destination;
    }
    
    public void setDestinationLocation(Location destination){
        this.destination = destination;
        destLat = destination.getLatitude(); 
        destLon = destination.getLongitude();       
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
