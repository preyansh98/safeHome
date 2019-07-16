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

        //front end they have selected a logged in walker, and pass it in.
        Walker walkerRole = (Walker) (Walker.getRole(walkerID));
        if(walkerRole == null) throw new IllegalStateException("The walker does not exist");

        //what do i need to create an assignment? student id and walker id
        try{
            assignmentService.createAssignmentService(studentRole, walkerRole);
        }
        catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    /**
     * This algorithm has the following requirements:
     * 1. first verify if student is valid
     * 2. verify if student pas passed create request stage.
     * 3. verify if request is valid.
     *
     * assuming all is valid
     *
     * 4. query all currently logged in walkers -> these should have correct status.
     * 5. out of these, sort them based on best match for student.
     * ----->This best match should be based off a weighted mean avg of: location,rating,verified, familiarity(post-mvp)
     *
     * 6. return the list, if it could be sorted in-place that'd be cool.
     */
    public List<Walker> getAllPotentialWalkers(int mcgillID) {
        long currenttime = System.currentTimeMillis();
        List<Walker> sortedWalkers;

        //Student checks
        Student student = (Student) Student.getRole(mcgillID);
        UserRole role = SafeHomeApplication.getLoggedInUsersMap().get(mcgillID);

        student = (Student) role;

        if(student==null)
            throw new IllegalStateException("The student for this McGill ID does not exist");

        //Request Check
        if(student.getRequest()!=null && student.getRequest().getAssignment()==null
            && student.getRequest().getRequestStatus().equals(RequestStatus.CREATED)){
            //all is valid

            //externalize this call to another service for reusability later.

            List<Walker> loggedInWalkerInstances = new ArrayList<>();

            SafeHomeApplication.getLoggedInUsersMap().entrySet().stream()
                        .filter(entry -> entry.getValue() instanceof Walker)
                        .forEach(walkerEntry->{
                            loggedInWalkerInstances.add( (Walker) walkerEntry.getValue());
                        });

            if(!loggedInWalkerInstances.isEmpty()){
                sortedWalkers = sortWalkersForReq(loggedInWalkerInstances, student.getRequest());
            }
            else{
                return Collections.EMPTY_LIST;
            }

            System.out.println("POTENTIAL WALKERS-END, took: " + (System.currentTimeMillis()-currenttime));
            return sortedWalkers;
        }
        else
            throw new IllegalStateException("The request is not eligible for potential walkers at this stage");
    }

    //TODO: nothing is getting sorted yet.
    private List<Walker> sortWalkersForReq(List<Walker> loggedInWalkerInstances, SpecificRequest studentRequest){
        List<Walker> sortedWalkers = new ArrayList<>();

        LinkedHashMap<Walker, Double> walkerWithScoreMap = new LinkedHashMap<>();

        loggedInWalkerInstances.forEach(walker ->{
            walkerWithScoreMap.put(walker, calculateScore(walker));
        });

        //now sort walkers based on values and return.
        return new ArrayList<>(walkerWithScoreMap.entrySet().stream()
                            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new))
                            .keySet());

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