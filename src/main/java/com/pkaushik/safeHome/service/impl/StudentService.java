package com.pkaushik.safeHome.service.impl;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.SafeHomeUser;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.service.StudentServiceIF;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentService implements StudentServiceIF {


    @Override
    public void selectWalkerForRequestService(int studentID, int walkerID) {
        //check if student has made a request.
        SafeHomeUser user = SafeHomeUser.getUser(studentID);
        Student studentRole = (Student) user.getRoles().stream().filter((x) -> (x instanceof Student)).findAny().orElse(null);
        if(studentRole == null) throw new RuntimeException("The student does not exist");

        if(studentRole.getRequest() == null) throw new IllegalAccessError("A request has to be created before a walker is selected");

        //front end they have selected a logged in walker, and pass it in.
        SafeHomeUser walkerUser = SafeHomeUser.getUser(walkerID);
        Walker walkerRole = (Walker) (walkerUser.getRoles().stream().filter((x) -> (x instanceof Walker))).findAny().orElse(null);
        if(walkerRole == null) throw new RuntimeException("The walker does not exist");

        UUID assignmentID = UUID.randomUUID();
        Assignment walkerAssignment = new Assignment(assignmentID, studentRole.getRequest(), walkerRole);
        //walker has to accept or deny it.
        SafeHomeApplication.addAssignmentToMap(assignmentID, walkerAssignment);
        studentRole.getRequest().setAssignment(walkerAssignment);
    }
}