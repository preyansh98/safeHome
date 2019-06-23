package com.pkaushik.safeHome.service.impl;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.SafeHomeUser;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.service.AssignmentServiceIF;
import com.pkaushik.safeHome.service.StudentServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentService implements StudentServiceIF {

    @Autowired
    private AssignmentServiceIF assignmentService;


    @Override
    public void selectWalkerForRequestService(int studentID, int walkerID) {
        //check if student has made a request.
        SafeHomeUser user = SafeHomeUser.getUser(studentID);
        Student studentRole = (Student) user.getRoles().stream().filter((x) -> (x instanceof Student)).findAny().orElse(null);
        if(studentRole == null) throw new IllegalStateException("The student does not exist");

        if(studentRole.getRequest() == null) throw new IllegalStateException("A request has to be created before a walker is selected");

        //front end they have selected a logged in walker, and pass it in.
        SafeHomeUser walkerUser = SafeHomeUser.getUser(walkerID);
        Walker walkerRole = (Walker) (walkerUser.getRoles().stream().filter((x) -> (x instanceof Walker))).findAny().orElse(null);
        if(walkerRole == null) throw new IllegalStateException("The walker does not exist");

        //what do i need to create an assignment? student id and walker id
        try{
            assignmentService.createAssignmentService(studentRole, walkerRole);
        }
        catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }
}