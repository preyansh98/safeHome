package com.pkaushik.safeHome.service;

import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.Walker;

import java.util.UUID;

public interface AssignmentServiceIF{

    Assignment findAssignmentByUUIDService(UUID assignmentID);

    void createAssignmentService(Student studentRole, Walker walkerRole);

    Assignment getCurrentAssignmentService(int mcgillID);

    void updateAssignmentService(int mcgillID);

    void cancelAssignmentService(int mcgillID);

    void acceptAssignmentByWalkerService(Assignment assignment);

}