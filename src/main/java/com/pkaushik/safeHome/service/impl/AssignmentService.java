package com.pkaushik.safeHome.service.impl;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.repository.AssignmentRepository;
import com.pkaushik.safeHome.service.AssignmentServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignmentService implements AssignmentServiceIF {

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Override
    public Assignment findAssignmentByUUIDService(UUID assignmentID) {
        Optional<Assignment> optional = assignmentRepo.findById(assignmentID);

        if(optional.isPresent())
            return optional.get();
        else
            return null;
    }

    @Override
    public void createAssignmentService(Student studentRole, Walker walkerRole) {
        UUID assignmentID = UUID.randomUUID();

        //student must have request.
        try{
            studentRole.getRequest();
        }
        catch(NullPointerException e){
            throw new IllegalStateException("Student does not have a request");
        }

        //student has a request. check that walker has no assignment.
        if(walkerRole.getCurrentAssignment()!=null) throw new IllegalStateException("Walker already has an assignment");

        //walker is now free, but walker has to decide whether to accept/deny assignment.
        Assignment potentialAssignment = new Assignment(assignmentID, studentRole.getRequest(), walkerRole);

        //pipeline a prompt walker action.
        this.promptWalkerForAssignmentInternal(potentialAssignment);
    }

    @Override
    public Assignment getCurrentAssignmentService(int mcgillID) {
        SafeHomeUser user = SafeHomeUser.getUser(mcgillID);

        Student student = (Student) user.getRoles().stream().filter((role) -> role instanceof Student)
                .findAny()
                .orElse(null);

        if(student == null) throw new IllegalStateException("No student found with this id");
        if(student.getRequest().getAssignment() == null) throw new IllegalStateException("No assignment for this request");

        if(student.getRequest() != null && student.getRequest().getAssignment()!=null){
            Optional<Assignment> assignment = assignmentRepo.findById(student.getRequest().getAssignment().getAssignmentID());
            if(assignment.isPresent()) return assignment.get();
            else throw new EntityNotFoundException("Assignment isn't present in DB");
        }

        return null;

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


    private void promptWalkerForAssignmentInternal(Assignment assignment) {

        Walker proposedWalkerForAssignment = assignment.getWalker();
        SpecificRequest requestForAssignment = assignment.getRequest();
        Student studentAskingForAssignment = assignment.getRequest().getStudent();

        //to prompt we will just add assignment to map.
        SafeHomeApplication.addAssignmentToMap(assignment, proposedWalkerForAssignment);
    }

    public void cancelAssignmentService(Assignment assignment){
        assignment.deleteAssignment();
        assignmentRepo.save(assignment);
        assignmentRepo.delete(assignment);
    }

}
