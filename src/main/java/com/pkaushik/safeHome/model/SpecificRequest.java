package com.pkaushik.safeHome.model;

import java.util.UUID;

public class SpecificRequest {
    
    public enum RequestStatus{
        CREATED, SEARCHING, ASSIGNED, ENROUTE, COMPLETE
    }

    Student student;
    Location pickupLocation; 
    DateTime pickupTime; 
    Location destination;  
    Walker walker;
    RequestStatus requestStatus; 
    Assignment assignment; 
    
    
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
