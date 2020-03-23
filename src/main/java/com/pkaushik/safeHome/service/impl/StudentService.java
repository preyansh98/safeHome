package com.pkaushik.safeHome.service.impl;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.model.enumerations.RequestStatus;
import com.pkaushik.safeHome.repository.StudentRepository;
import com.pkaushik.safeHome.service.AssignmentServiceIF;
import com.pkaushik.safeHome.service.StudentServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class StudentService implements StudentServiceIF {

    @Autowired
    private AssignmentServiceIF assignmentService;

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public void selectWalkerForRequestService(int studentID, int walkerID) {
        //check if student has made a request.
        Student studentRole = (Student) studentRepository.findById(studentID).get();
        if(studentRole == null) throw new IllegalStateException("The student does not exist");

        if(studentRole.getRequest() == null) throw new IllegalStateException("A request has to be created before a walker is selected");

        //get selected walker
        Walker walkerRole = (Walker) (Walker.getRole(walkerID));
        if(walkerRole == null) throw new IllegalStateException("The walker does not exist");

        try{
            assignmentService.createAssignmentService(studentRole, walkerRole);
        }
        catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    /**
     * query all currently logged in walkers -> these should have correct status.
     * out of these, sort them based on best match for student.
     * ----->This best match should be based off a weighted mean avg of: location,rating,verified, familiarity(post-mvp)
     */
    public List<Walker> getAllPotentialWalkers(int mcgillID) {
        long startTime = System.currentTimeMillis();

        //Student checks
        Student student = (Student) SafeHomeApplication.getLoggedInUsersMap().get(mcgillID);

        if (student == null)
            throw new IllegalStateException("The student for this McGill ID does not exist");

        //TODO: externalize this call to another service for reusability later.
        List<Walker> loggedInWalkerInstances = new ArrayList<>();

        SafeHomeApplication.getLoggedInUsersMap().entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Walker)
                .forEach(walkerEntry -> {
                    loggedInWalkerInstances.add((Walker) walkerEntry.getValue());
                });

        if (!loggedInWalkerInstances.isEmpty()) {
            loggedInWalkerInstances.sort(Comparator.comparingDouble(walker -> calculateScore((Walker) walker))
                    .reversed());

            System.out.println("POTENTIAL WALKERS-END, took: " + (System.currentTimeMillis() - startTime));
            return loggedInWalkerInstances;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private Double calculateScore(Walker walker){
        double score = 0;

        if(walker.isWalksafe()){
            score+=.5;
            score+=((walker.getRating())/5)*(0.5);
        }
        else{
            score=(walker.getRating()/5);
        }

        return score*100;
    }
}