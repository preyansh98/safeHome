package com.pkaushik.safeHome.controller.QueryControllers;

import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.repository.WalkerRepository;
import com.pkaushik.safeHome.service.AssignmentServiceIF;
import com.pkaushik.safeHome.service.WalkerServiceIF;
import com.pkaushik.safeHome.service.impl.AssignmentService;
import com.pkaushik.safeHome.validation.InputValidationIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WalkerQueryController {

    @Autowired
    private InputValidationIF inputValidator;

    @Autowired
    private WalkerServiceIF walkerService;

    @Autowired
    private AssignmentServiceIF assignmentService;

    @Autowired
    private WalkerRepository walkerRepository;

    @GetMapping(value="/get_walker_profile/{mcgillID}")
    public ResponseEntity<Walker> getWalkerProfile(@PathVariable int mcgillID){

        try{
            inputValidator.validateMcgillID(mcgillID);
        } catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Walker> walkerOpt = walkerRepository.findById(mcgillID);

        return (walkerOpt.isPresent()) ? new ResponseEntity<>(walkerOpt.get(), HttpStatus.OK) :
                                            new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value="/get_walker_assignment/{mcgillID}")
    public ResponseEntity<Assignment> getWalkerAssignment(@PathVariable int mcgillID){

        try{
            inputValidator.validateMcgillID(mcgillID);
        } catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Assignment assignment = assignmentService.getCurrentAssignmentService(mcgillID);

        return (assignment!=null) ? (new ResponseEntity<>(assignment, HttpStatus.OK)) :
                                        (new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
