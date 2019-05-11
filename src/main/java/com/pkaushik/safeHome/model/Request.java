package com.pkaushik.safeHome.model;

public class Request {

    private static Request instance = null; 

    private Request(){

    }

    public static Request getInstance(){
        if(instance != null){
            return instance; 
        }
        else{
            instance = new Request(); 
            return instance; 
        }
    }

    Location pickupLocation; 
    DateTime currentTime; 
    Location destination; 
}
