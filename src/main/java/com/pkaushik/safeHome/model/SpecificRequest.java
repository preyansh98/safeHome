package com.pkaushik.safeHome.model;

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


    public SpecificRequest(Student student, Location pickupLocation, Location destination){
        this.student = student; 
        this.pickupLocation = pickupLocation; 
        this.destination = destination; 
    }

    public SpecificRequest(Student student, double pickupLatitude, double pickupLongitude,   
    double destinationLatitude, double destinationLongitude){
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
}
