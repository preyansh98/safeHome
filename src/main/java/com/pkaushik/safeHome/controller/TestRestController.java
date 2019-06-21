package com.pkaushik.safeHome.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.pojo;
import com.pkaushik.safeHome.repository.StudentRepository;
import com.pkaushik.safeHome.repository.WalkerRepository;
import com.pkaushik.safeHome.repository.pojorepository;
import com.pkaushik.safeHome.service.impl.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {



    @Autowired
    private WalkerRepository repo; 

    @Autowired
    private StudentRepository studentRepo; 

    @GetMapping("/bulkcreate")
public String bulkcreate(){
// save a single Customer
    repo.save(new Walker(1, SafeHome.getSafeHomeInstance(), true));
    // save a list of Customers
 
    return "were created";
}

@GetMapping("/dbcall")
public String resp(){
    Student stu = new Student(260790402, SafeHome.getSafeHomeInstance()); 
    studentRepo.save(stu); 
   // String s = studentRepo.findById(260790402).get().toString(); 
    return "";  
}
}