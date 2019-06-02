package com.pkaushik.safeHome.controller;

import java.util.UUID;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.SpecificRequest;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.Walker.walkerStatus;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalkerModController {

    /**
     * TODO: Add checks for: 
     * walker should be open for assignments
     * request should exist for assignment
     * assignment should have not been accepted already
     * @param mcgillID
     * @param assignmentID
     */
    public static void acceptAssignment(int mcgillID, UUID assignmentID){
        Walker walker = Walker.getWalker(mcgillID); 
        if(!walker.getStatus().equals(walkerStatus.SELECTED)) throw new IllegalStateException(" Walker must be selected before accepting an assignment"); 
        Assignment assignmentForWalker = SafeHomeApplication.getOpenAssignmentsMap().get(assignmentID); 
        if(assignmentForWalker.hasAccepted()) throw new IllegalStateException("The assignment has to be open to accept it"); 
        SpecificRequest requestForAssignment = assignmentForWalker.getRequest(); 
        if(requestForAssignment == null) throw new IllegalStateException("A request must be created to accept assignment"); 
       
        //assignment operations
        assignmentForWalker.isAccepted(true);
        SafeHomeApplication.removeAssignmentFromMap(assignmentID);

        //walker operations
        walker.setCurrentAssignment(assignmentForWalker);
        walker.setStatus(walkerStatus.ASSIGNED);

        //student ops for  later. 
    }

    //TODO: interactions between student and walker
    public static void refuseAssignment(int mcgillID, UUID assignmentID){
        Walker walker = Walker.getWalker(mcgillID); 
        Assignment assignmentForWalker = SafeHomeApplication.getOpenAssignmentsMap().get(assignmentID); 

        SpecificRequest requestForAssignment = assignmentForWalker.getRequest(); 

        //assignment operation
        assignmentForWalker.isAccepted(false);
        
        //ping student to select another walker
    }
}