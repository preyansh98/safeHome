package com.pkaushik.safeHome.controller;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.SafeHomeUser;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.service.impl.StudentService;
import com.pkaushik.safeHome.validation.InputValidationIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.pkaushik.safeHome.utils.JsonResponseConstants.JSON_SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private InputValidationIF inputValidator;

    @PostMapping(value="/selectWalkerReq/{studentID}")
    public ResponseEntity<String> selectWalkerForRequest(@PathVariable(name="studentID") int studentID, @RequestParam int walkerID){

        try{
            inputValidator.validateMcgillID(studentID);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        try{
            studentService.selectWalkerForRequestService(studentID, walkerID);
        }
        catch(IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JSON_SUCCESS_MESSAGE, HttpStatus.OK);
    }


}
