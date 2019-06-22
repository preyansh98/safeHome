package com.pkaushik.safeHome.service.impl;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.Assignment;
import com.pkaushik.safeHome.repository.AssignmentRepository;
import com.pkaushik.safeHome.service.AssignmentServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AssignmentService implements AssignmentServiceIF {

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Override
    public void createAssignmentService(int mcgillID) {

    }

    @Override
    public void getCurrentAssignmentService(int mcgillID) {

    }

    @Override
    public void updateAssignmentService(int mcgillID) {

    }

    @Override
    public void cancelAssignmentService(int mcgillID) {

    }

    @Override
    public void acceptAssignmentByWalkerService(Assignment assignmentForWalker) {

        assignmentForWalker.isAccepted(true);
        SafeHomeApplication.removeAssignmentFromMap(assignmentForWalker.getAssignmentID());
    }

    public void cancelAssignmentService(Assignment assignment){
        assignment.deleteAssignment();
        assignmentRepo.save(assignment);
        assignmentRepo.delete(assignment);
    }

}
