package com.pkaushik.safeHome.controller;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.SafeHomeUser;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.service.impl.StudentService;
import com.pkaushik.safeHome.validation.InputValidationIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private InputValidationIF inputValidator;


    public void selectWalkerForRequest(int studentID, int walkerID){

        try{
            inputValidator.validateMcgillID(studentID);
        }
        catch(IllegalArgumentException e){

        }
        try{
            inputValidator.validateMcgillID(walkerID);
        }
        catch(IllegalArgumentException e){

        }

        //do both exist in db?

        try{
            studentService.selectWalkerForRequestService(studentID, walkerID);
        }
        catch(Exception e){
            //handle error.
        }

        //handle correct resp
    }


}
