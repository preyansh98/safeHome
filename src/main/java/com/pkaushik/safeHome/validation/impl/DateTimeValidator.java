package com.pkaushik.safeHome.validation.impl;

import com.pkaushik.safeHome.exceptions.DateConventionException;
import com.pkaushik.safeHome.exceptions.ScheduleDateTimeException;
import com.pkaushik.safeHome.model.DateTime;
import com.pkaushik.safeHome.model.Schedule;
import com.pkaushik.safeHome.validation.DateTimeValidationIF;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateTimeValidator implements DateTimeValidationIF {

    @Override
    public void validateDayConvention(int day){
        //TODO: depending on the month, validate day to not have 30th of feb for example.
        if (!(day >= 1 && day<=31)) throw new DateConventionException();
    }

    @Override
    public void validateMonthConvention(int month){
        if (!(month >=1 && month<=12)) throw new DateConventionException();
    }

    @Override
    public void validateYearConvention(int year){
        if (!(year >= Calendar.getInstance().get(Calendar.YEAR))) throw new DateConventionException();
    }

    @Override
    public void validateHourConvention(int hour){
        if (!(hour >=0 && hour<=24)) throw new DateConventionException();
    }

    @Override
    public void validateMinConvention(int min){
        if (!(min >= 0 && min<=60)) throw new DateConventionException();
    }

    @Override
    public void validateStartBeforeEnd(int startDay, int startMonth, int startYear,
                                       int endDay, int endMonth, int endYear,
                                            int startHour, int startMin, int endHour, int endMin) {


        Date startDateDummy = new Date(startDay, startMonth, startYear, startHour, startMin,0);
        Date endDateDummy = new Date(endDay, endMonth, endYear, endHour, endMin, 0);

        if(startDateDummy.after(endDateDummy)) throw new ScheduleDateTimeException("Start date can't be after end date!");

    }

    @Override
    public void validateSchedule(Schedule schedule){

        DateTime start = schedule.getStartDate();
        DateTime end = schedule.getEndDate();


        validateDayConvention(Integer.parseInt(start.getDay()));
        validateHourConvention(Integer.parseInt(start.getHour()));
        validateMinConvention(Integer.parseInt(start.getMins()));
        validateMonthConvention(Integer.parseInt(start.getMonth()));
        validateYearConvention(Integer.parseInt(start.getYear()));
        validateDayConvention(Integer.parseInt(end.getDay()));
        validateHourConvention(Integer.parseInt(end.getHour()));
        validateMinConvention(Integer.parseInt(end.getMins()));
        validateMonthConvention(Integer.parseInt(end.getMonth()));
        validateYearConvention(Integer.parseInt(end.getYear()));

        validateStartBeforeEnd(Integer.parseInt(start.getDay()), Integer.parseInt(start.getMonth()),
                Integer.parseInt(start.getYear()), Integer.parseInt(end.getDay()), Integer.parseInt(end.getMonth()),
                Integer.parseInt(end.getYear()), Integer.parseInt(start.getHour()), Integer.parseInt(start.getMins()),
                Integer.parseInt(end.getHour()), Integer.parseInt(end.getMins()));

    }


}
