package com.pkaushik.safeHome.controller.QueryControllers.Async;

import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.service.WalkerServiceIF;
import com.pkaushik.safeHome.validation.InputValidationIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WalkerAsyncQueryController {

    @Autowired
    private InputValidationIF inputValidator;

    @Autowired
    private WalkerServiceIF walkerService;

    @GetMapping(value="/check_any_assignments/{mcgillID}")
    public ResponseEntity<Assignment> checkProposedAssignments(@PathVariable(name="mcgillID") int mcgillID){

        try{
            inputValidator.validateMcgillID(mcgillID);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Walker walkerRole = null;

        try{
            walkerRole = (Walker) Walker.getRole(mcgillID);
        }
        catch(IllegalAccessError e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //walker is valid.
        Assignment assignmentForWalker = null;

        try{
            assignmentForWalker = walkerService.getWalkerProposedAssignmentsService(walkerRole);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(assignmentForWalker, HttpStatus.OK);

    }
}
