package com.pkaushik.safeHome.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.pkaushik.safeHome.model.pojo;
import com.pkaushik.safeHome.repository.pojorepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {



    @Autowired
    private pojorepository repo; 

    @GetMapping("/bulkcreate")
public String bulkcreate(){
// save a single Customer
    repo.save(new pojo(1, "true"));
    // save a list of Customers
         repo.saveAll(Arrays.asList(
            new pojo(12, "false"), new pojo(5, "adfa"), new pojo(2, "adfadfa"), new pojo(3, "lmao")
                     
                       )); 
    return "were created";
}
}