package com.pkaushik.safeHome.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.model.Walker.walkerStatus;

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
        Assignment assignmentForWalker = SafeHomeApplication.getOpenAssignmentsMap().get(assignmentID); 
        
        SpecificRequest requestForAssignment = assignmentForWalker.getRequest(); 

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