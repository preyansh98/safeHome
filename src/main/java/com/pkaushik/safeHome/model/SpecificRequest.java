package com.pkaushik.safeHome.model;

public class SpecificRequest {
    
    Student student;
    Location pickupLocation; 
    DateTime pickupTime; 
    Location destination;  

    public SpecificRequest(Student student, Location pickupLocation, Location destination, DateTime pickupTime){
        this.student = student; 
        this.pickupLocation = pickupLocation; 
        this.pickupTime = pickupTime; 
        this.destination = destination; 
    }

    public Student getStudent(){
        return student; 
    }

    public void setStudent(Student student){
        this.student = student; 
    }

    //more getters and setters to follow. 


}
