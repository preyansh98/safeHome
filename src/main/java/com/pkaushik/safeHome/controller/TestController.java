package com.pkaushik.safeHome.controller;

import com.pkaushik.safeHome.model.Location;
import com.pkaushik.safeHome.model.SpecificRequest;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.repository.RequestRepository;
import com.pkaushik.safeHome.repository.StudentRepository;
import com.pkaushik.safeHome.service.RequestServiceIF;
import com.pkaushik.safeHome.service.UserAuthServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.pkaushik.safeHome.utils.TestConstants.*;


@RestController
public class TestController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestServiceIF requestService;

    @Autowired
    private UserAuthServiceIF userAuthService;

    @GetMapping("/yo")
    public List<Student> studentAndRequest() throws RuntimeException{

        userAuthService.registerService(testValidPhoneNo, testValidMcgillID,false);
        userAuthService.loginService(testValidMcgillID,false);

        Student student = (Student) Student.getRole(testValidMcgillID);
        if(student!=null) System.out.println("found student!");

        SpecificRequest request = requestService.createRequestService(testValidMcgillID, 12,12,2,2);
        

        return(studentRepository.findAll());
    }

    @GetMapping("/yoo")
    public SpecificRequest testReq() throws RuntimeException{
        userAuthService.registerService(testValidPhoneNo, testValidMcgillID,false);
        userAuthService.loginService(testValidMcgillID,false);

        Student student = (Student) Student.getRole(testValidMcgillID);
        if(student!=null) System.out.println("found student!");

        SpecificRequest request = requestService.createRequestService(testValidMcgillID, 12,12,2,2);

        return (requestRepository.findById(request.getRequest_id()).get());
    }
}
