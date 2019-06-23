package com.pkaushik.safeHome.controller;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.exceptions.DateConventionException;
import com.pkaushik.safeHome.exceptions.ScheduleDateTimeException;
import com.pkaushik.safeHome.service.WalkerServiceIF;
import com.pkaushik.safeHome.service.impl.WalkerService;
import com.pkaushik.safeHome.validation.DateTimeValidationIF;
import com.pkaushik.safeHome.validation.InputValidationIF;
import com.pkaushik.safeHome.validation.WalkerPropertiesValidationIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RestController
public class WalkerController {

    @Autowired
    private WalkerServiceIF walkerService;

    @Autowired
    private DateTimeValidationIF dateValidator;

    @Autowired
    private InputValidationIF inputValidator;

    @Autowired
    private WalkerPropertiesValidationIF walkerPropertiesValidationIF;

    public void acceptAssignment(int mcgillID, UUID assignmentID){
        try{
            inputValidator.validateMcgillID(mcgillID);
        }
        catch(IllegalArgumentException e){
            //handle error resp
        }

        try{
            walkerService.acceptAssignmentService(mcgillID, assignmentID);
        }
        catch(IllegalAccessError e){
            //handle error
        }
        catch(IllegalStateException e){
            //handle
        }

        //success resp
    }

    public void refuseAssignment(int mcgillID, UUID assignmentID){
        try{
            inputValidator.validateMcgillID(mcgillID);
        }
        catch(IllegalArgumentException e){
            //handle error resp
        }

        try{
            walkerService.refuseAssignmentService(mcgillID, assignmentID);
        }
        catch(Exception e){
            //handle error
        }

        //success resp
    }


    public void setWalkerSchedule(int mcgillID, int startDay, int startMonth, int startYear,
                                         int endDay, int endMonth, int endYear, int startHour, int startMin, int endHour, int endMin) {

        try{
            dateValidator.validateDayConvention(startDay);
            dateValidator.validateDayConvention(endDay);
            dateValidator.validateMonthConvention(startMonth);
            dateValidator.validateMonthConvention(endMonth);
            dateValidator.validateYearConvention(startYear);
            dateValidator.validateYearConvention(endYear);
            dateValidator.validateHourConvention(startHour);
            dateValidator.validateHourConvention(endHour);
            dateValidator.validateMinConvention(startMin);
            dateValidator.validateMinConvention(endMin);
        }
        catch(DateConventionException e){
            //handle error resp
        }

        try{
            dateValidator.validateStartBeforeEnd(startDay, startHour, startYear, endDay, endMonth, endYear, startHour, startMin, endHour,endMin);
        }
        catch(ScheduleDateTimeException e){
            //handle error resp
        }

        //dates are valid, set new schedule.

        try{
            walkerService.setWalkerScheduleService(mcgillID, startDay,startMonth,startYear,endDay,endMonth,endYear,startHour,startMin,endHour,endMin);
        }
        catch (EntityNotFoundException e){
            //does not exist in db
        }
        catch(IllegalStateException e){
            //role not specified.tell fe to register as walker to do this.
        }

        //correct resp here.
    }



    /**
     * Enter all params as -1, apart from the one that wants to be changed.
     * @param mcgillID
     * @param startDay
     * @param startMonth
     * @param startYear
     * @param endDay
     * @param endMonth
     * @param endYear
     * @param startHour
     * @param startMin
     * @param endHour
     * @param endMin
     */
    public void changeWalkerSchedule(int mcgillID, int startDay, int startMonth, int startYear,
                                            int endDay, int endMonth, int endYear, int startHour, int startMin, int endHour, int endMin){
        //TODO: Add checks to ensure parameters follow date-time convention.

        try{
            dateValidator.validateDayConvention(startDay);
            dateValidator.validateDayConvention(endDay);
            dateValidator.validateMonthConvention(startMonth);
            dateValidator.validateMonthConvention(endMonth);
            dateValidator.validateYearConvention(startYear);
            dateValidator.validateYearConvention(endYear);
            dateValidator.validateHourConvention(startHour);
            dateValidator.validateHourConvention(endHour);
            dateValidator.validateMinConvention(startMin);
            dateValidator.validateMinConvention(endMin);
        }
        catch(DateConventionException e){
            //handle error resp
        }

        try{
            dateValidator.validateStartBeforeEnd(startDay, startHour, startYear, endDay, endMonth, endYear, startHour, startMin, endHour,endMin);
        }
        catch(ScheduleDateTimeException e){
            //handle error resp
        }

        try{
            walkerService.changeWalkerScheduleService(mcgillID, startDay,startMonth,startYear,endDay,endMonth,endYear,startHour,startMin,endHour,endMin);
        }
        catch (EntityNotFoundException e){
            //does not exist in db
        }
        catch(IllegalStateException e){
            //role not specified.tell fe to register as walker to do this.
        }

        //return correct resp
    }

    public void walkerIsWalksafe(int mcgillID, boolean isWalksafe){

        try{
            inputValidator.validateMcgillID(mcgillID);
        }
        catch(IllegalArgumentException e){
            //handle error resp
        }

        try{
            walkerService.walkerIsWalksafeService(mcgillID, isWalksafe);
        }
        catch(IllegalStateException e){
            //handle
        }

        //return correct resp
    }

    public void updateWalkerRating(int mcgillID, double newRating){

        try{
            inputValidator.validateMcgillID(mcgillID);
        }
        catch(IllegalArgumentException e){
            //handle
        }

        if(walkerPropertiesValidationIF.validateRating(newRating)){
            try{
                walkerService.updateWalkerRatingService(mcgillID, newRating);
            }
            catch(IllegalStateException e){
                //handle
            }
        }
        else{
            //tell fe that rating is not valid.
        }
    }
}
