package com.pkaushik.safeHome.controller;

import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.service.WalkerServiceIF;
import com.pkaushik.safeHome.validation.InputValidationIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalkerAsyncQueryController {

    @Autowired
    private InputValidationIF inputValidator;

    @Autowired
    private WalkerServiceIF walkerService;

    public Assignment checkProposedAssignments(int mcgillID){

        try{
            inputValidator.validateMcgillID(mcgillID);
        }
        catch(IllegalArgumentException e){
            //handle error resp
        }

        Walker walkerRole = null;

        try{
            walkerRole = (Walker) Walker.getRole(mcgillID);
        }
        catch(IllegalAccessError e){
            //handle error resp.
        }

        //walker is valid.
        Assignment assignmentForWalker = null;

        try{
            assignmentForWalker = walkerService.getWalkerProposedAssignmentsService(walkerRole);
        }
        catch(Exception e){
            //handle
        }

        return assignmentForWalker;

    }
}
