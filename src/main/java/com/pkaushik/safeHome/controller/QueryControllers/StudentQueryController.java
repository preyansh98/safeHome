package com.pkaushik.safeHome.controller.QueryControllers;

import com.pkaushik.safeHome.model.SpecificRequest;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.repository.StudentRepository;
import com.pkaushik.safeHome.service.StudentServiceIF;
import com.pkaushik.safeHome.validation.InputValidationIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentQueryController {

    @Autowired
    private InputValidationIF inputValidator;

    @Autowired
    private StudentServiceIF studentService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value = "/view_walkers/{mcgillID}")
    public ResponseEntity<List<Walker>> viewPotentialWalkers(@PathVariable int mcgillID){
        List<Walker> potentialWalkers = null;

        try{
            inputValidator.validateMcgillID(mcgillID);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            potentialWalkers = studentService.getAllPotentialWalkers(mcgillID);
        }
        catch(IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(potentialWalkers, HttpStatus.OK);
    }

    @GetMapping(value="/get_curr_req/{mcgillID}")
    public ResponseEntity<SpecificRequest> getCurrentRequest(@PathVariable int mcgillID){

        try{
            inputValidator.validateMcgillID(mcgillID);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Student> studentOpt = studentRepository.findById(mcgillID);

        Student student = (studentOpt.isPresent()) ? (studentOpt.get()) : (null);

        if(student!=null)
            return new ResponseEntity<>(student.getRequest(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<List<SpecificRequest>> getPastRequests(@PathVariable int mcgillID){
        return null;
    }
}
