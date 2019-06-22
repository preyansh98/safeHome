package com.pkaushik.safeHome.service;

import com.pkaushik.safeHome.model.Assignment;

import java.util.UUID;

public interface AssignmentServiceIF{

    void createAssignmentService(int mcgillID);

    void getCurrentAssignmentService(int mcgillID);

    void updateAssignmentService(int mcgillID);

    void cancelAssignmentService(int mcgillID);

    void acceptAssignmentByWalkerService(Assignment assignment);
}