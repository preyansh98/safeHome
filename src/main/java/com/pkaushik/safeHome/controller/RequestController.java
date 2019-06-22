package com.pkaushik.safeHome.controller;

import com.pkaushik.safeHome.service.impl.RequestService;
import com.pkaushik.safeHome.validation.impl.InputValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    
    @Autowired
    private RequestService requestService; 

    @Autowired 
    private InputValidator inputValidator;
    
//CRUD Request
	
/**
 * A request is created by the student for a walker. 
 * For MVP, assume all times are timestamped and are immediate.
 * @param mcgillID
 * @param pickupLatitude
 * @param pickupLongitude
 * @param destinationLatitude
 * @param destinationLongitude
 */
public void createSpecificRequest(int mcgillID, double pickupLatitude, double pickupLongitude,
double destinationLatitude, double destinationLongitude) {
    
    //Validation
    try{
        inputValidator.validateMcgillID(mcgillID);
        inputValidator.validatePickup(pickupLatitude, pickupLongitude);
        inputValidator.validateDestination(destinationLatitude, destinationLongitude);
    }
    catch(IllegalArgumentException e){
        
    }

    //Service
    try{
        requestService.createRequestService(mcgillID, pickupLatitude, pickupLongitude, destinationLatitude, destinationLongitude);
    }
    catch(Exception e){
        //treat erro        
    }

    //return response correctly
}

public void updateRequest(int mcgillID, double pickupLatitude, double pickupLongitude, double destinationLatitude, double destinationLongitude) {

    try{
        inputValidator.validateMcgillID(mcgillID);
    }
    catch(IllegalArgumentException e){
        //handle
    }

    try{
        requestService.updateRequestService(mcgillID, pickupLatitude, pickupLongitude, destinationLatitude, destinationLongitude);
    }
    catch(Exception e){
        //handle
    }

    //return response correctly
}

public void cancelRequest(int mcgillID) {
	
    try{
        inputValidator.validateMcgillID(mcgillID);
    }
    catch(IllegalArgumentException e){

    }

    try{
        requestService.cancelRequestService(mcgillID);
    }
    catch(Exception e){
        //handle
    }

    //return error resp
}
}