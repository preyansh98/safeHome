package com.pkaushik.safeHome.service.impl;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.*;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;
import com.pkaushik.safeHome.repository.WalkerRepository;
import com.pkaushik.safeHome.service.WalkerServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class WalkerService implements WalkerServiceIF {

    @Autowired
    private WalkerRepository walkerRepo;

    @Autowired
    private AssignmentService assignmentService;

    @Override
    public void setWalkerScheduleService(int mcgillID, int startDay, int startMonth, int startYear, int endDay,
                                         int endMonth, int endYear, int startHour, int startMin, int endHour,
                                         int endMin) {

        //have to ensure walker exists in db first
        if(!walkerRepo.existsById(mcgillID)) throw new EntityNotFoundException("Walker must exist in DB");

        if (SafeHomeUser.getUser(mcgillID) != null && SafeHomeUser.getUser(mcgillID).getRoles() != null) {
            Walker walkerRole = (Walker) (SafeHomeUser.getUser(mcgillID)).getRoles().stream()
                    .filter((role) -> (role instanceof Walker))
                    .findAny()
                    .orElse(null);

            if (walkerRole == null)
                throw new IllegalStateException("The walker whose schedule you are trying to set does not exist");

            Schedule newSchedule = new Schedule(startDay, startMonth, startYear, endDay, endMonth, endYear,
                    startHour, startMin, endHour, endMin);

            walkerRole.setSchedule(newSchedule);
            walkerRepo.save(walkerRole);
        }
    }

    @Override
    public void changeWalkerScheduleService(int mcgillID, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, int startHour, int startMin, int endHour, int endMin) {

        if(!walkerRepo.existsById(mcgillID)) throw new EntityNotFoundException("Walker must exist in DB");

        if (SafeHomeUser.getUser(mcgillID) != null && SafeHomeUser.getUser(mcgillID).getRoles() != null) {
            Walker walkerRole = (Walker) (SafeHomeUser.getUser(mcgillID)).getRoles().stream()
                    .filter((role) -> (role instanceof Walker))
                    .findAny()
                    .orElse(null);

            if (walkerRole == null)
                throw new IllegalStateException("The walker whose schedule you are trying to set does not exist");


            Schedule currentSchedule = walkerRole.getSchedule();
            if (currentSchedule == null)
                throw new IllegalStateException("No schedule exists to update. Please create a schedule first");

            //brute force
            if (startDay >= 0) currentSchedule.setStartDay(startDay);
            if (startMonth >= 0) currentSchedule.setStartMonth(startMonth);
            if (startYear >= 0) currentSchedule.setStartYear(startYear);
            if (endDay >= 0) currentSchedule.setEndDay(endDay);
            if (endMonth >= 0) currentSchedule.setEndMonth(endMonth);
            if (endYear >= 0) currentSchedule.setEndYear(endYear);
            if (startHour >= 0) currentSchedule.setStartHour(startHour);
            if (startMin >= 0) currentSchedule.setStartMin(startMin);
            if (endHour >= 0) currentSchedule.setEndHour(endHour);
            if (endMin >= 0) currentSchedule.setEndYear(endYear);
        }
    }

    @Override
    public void acceptAssignmentService(int mcgillID, UUID assignmentID) {

        Walker walker = Walker.getWalker(mcgillID);

        if(!walker.getStatus().equals(WalkerStatus.SELECTED))
            throw new IllegalStateException(" Walker must be selected before accepting an assignment");

        Assignment assignmentForWalker = SafeHomeApplication.getOpenAssignmentsMap().get(assignmentID);
        if(assignmentForWalker.hasAccepted()) throw new IllegalStateException("The assignment has to be created and open to accept it");

        SpecificRequest requestForAssignment = assignmentForWalker.getRequest();
        if(requestForAssignment == null) throw new IllegalStateException("A request must be created to accept assignment");

        //assignment operations
        assignmentService.acceptAssignmentByWalkerService(assignmentForWalker);

        //walker operations
        walker.setCurrentAssignment(assignmentForWalker);
        walker.setStatus(WalkerStatus.ASSIGNED);

        //TODO: student will have to be notified on this.
        //idea: create a url based on assignment uuid that fe will make async requests to
        //we will update what that finds here.
    }

    @Override
    public void refuseAssignmentService(int mcgillID, UUID assignmentID) {

        Walker walker = Walker.getWalker(mcgillID);
        Assignment assignmentForWalker = SafeHomeApplication.getOpenAssignmentsMap().get(assignmentID);

        SpecificRequest requestForAssignment = assignmentForWalker.getRequest();

        //assignment operation
        assignmentForWalker.isAccepted(false);

        //TODO: ping student to select another walker
    }

    @Override
    public void walkerIsWalksafeService(int mcgillID, boolean isWalksafe) {

        Walker walker = Walker.getWalker(mcgillID); //might have to get from db?

        if(walker == null)
            throw new IllegalStateException("No walker with ID exists");

        walker.setWalksafe(isWalksafe);
        walkerRepo.save(walker);
    }

    @Override
    public void updateWalkerRatingService(int mcgillID, double newRating) {
        Walker walker = Walker.getWalker(mcgillID); //might have to get from db?

        if(walker == null)
            throw new IllegalStateException("No walker with ID exists");

        walker.setRating(newRating);
        walkerRepo.save(walker);
    }


}