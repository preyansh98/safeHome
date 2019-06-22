package com.pkaushik.safeHome.service.impl;

import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.model.enumerations.RequestStatus;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;
import com.pkaushik.safeHome.repository.AssignmentRepository;
import com.pkaushik.safeHome.repository.RequestRepository;
import com.pkaushik.safeHome.repository.StudentRepository;
import com.pkaushik.safeHome.repository.WalkerRepository;
import com.pkaushik.safeHome.service.AssignmentServiceIF;
import com.pkaushik.safeHome.service.RequestServiceIF;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

import com.pkaushik.safeHome.SafeHomeApplication;

public class RequestService implements RequestServiceIF{
    
    @Autowired
    private RequestRepository requestRepo; 

    @Autowired
    private StudentRepository studentRepo; 

    @Autowired
    private WalkerRepository walkerRepo; 

    @Autowired
    private AssignmentService assignmentService;
    
    public void createRequestService(int mcgillID, double pickupLatitude, double pickupLongitude,
double destinationLatitude, double destinationLongitude) {
	
	UserRole role = SafeHomeApplication.getLoggedInUsersMap().get(mcgillID);

	//Only student can create a request.
	if(role instanceof Walker) throw new IllegalArgumentException("A Walker can not create a pickup request"); 
	
	Student student = (Student) role;

	if(((Student) role).getRequest() == null)
	    throw new IllegalStateException("Student already has an existing request");

	SpecificRequest specificRequest = null; 

	//creates locations here instead of making extra call to model to create locations. 
	Location pickupLocation = null, destinationLocation = null; 
	
	try{
	pickupLocation = new Location(pickupLatitude, pickupLongitude); 
	}
	catch(Exception e){
		throw new IllegalArgumentException("Exception creating a pickup location"); 
	}

	try{
		destinationLocation = new Location(destinationLatitude, destinationLongitude); 
	}
	catch(Exception e){
		throw new IllegalArgumentException("Error setting your destination"); 
	}

	if(pickupLocation != null && destinationLocation != null){
	try{
		specificRequest = new SpecificRequest(student, pickupLocation, destinationLocation); 
	}
	catch(Exception e){
		throw new IllegalArgumentException(e.getMessage());
	}
    }

    //store all new entities created. 
    requestRepo.save(specificRequest); 

	if(specificRequest != null){
		student.setRequest(specificRequest);
		SafeHomeApplication.addNewRequest(specificRequest, 
                new ArrayList<Location>(Arrays.asList(pickupLocation, destinationLocation)));
        studentRepo.save(student); 
	}
}

    @Override
    public void listAllRequestsCreatedByStudentService(int mcgillID) {

    }

    @Override
    public void listAllPastRequestsForWalkerService(int mcgillID) {

    }

    @Override
    public void getCurrentRequestService(int mcgillID) {

    }

    @Override
    public void updateRequestService(int mcgillID, double pickupLatitude, double pickupLongitude, double destinationLatitude, double destinationLongitude) {

        //get the current request made by the student.
        
        Student studentRole = (Student) (SafeHomeUser.getUser(mcgillID).getRoles().stream()
            .filter((x) -> (x instanceof Student))
            .findAny()
            .orElse(null));
            
        if(studentRole!=null){
            SpecificRequest req = studentRole.getRequest(); 
            if(req!=null){
                //update the actions. 
                if(pickupLatitude != -1 || pickupLongitude != -1){
                Location newPickupLocation = new Location(pickupLatitude, pickupLongitude); 
                req.setPickupLocation(newPickupLocation);
                }
                if(destinationLatitude != -1 || destinationLongitude != -1){
                Location newDestinationLocation = new Location(destinationLatitude, destinationLongitude); 
                req.setDestinationLocation(newDestinationLocation);
                }
                requestRepo.save(req);
            }
        }
    }

    @Override
    public void cancelRequestService(int mcgillID) {

    //get request made by student
	//cancel it
	//remove its open assignment
	//if walker is assigned to it, remove walker from it. 
	
	Student studentRole = (Student) (SafeHomeUser.getUser(mcgillID).getRoles().stream()
                .filter((x) -> x instanceof Student)
                .findAny()
                .orElse(null));

    if(studentRole!=null){
        SpecificRequest req = studentRole.getRequest(); 
        if(req!=null){
            req.setRequestStatus(RequestStatus.CREATED);
            studentRole.setRequest(null);
            
            //get assignment mapped to this request. 
            Assignment assignment = studentRole.getRequest().getAssignment();
            assignmentService.cancelAssignmentService(assignment);
            requestRepo.save(req); 
            studentRepo.save(studentRole);
        }
    }

    }
}