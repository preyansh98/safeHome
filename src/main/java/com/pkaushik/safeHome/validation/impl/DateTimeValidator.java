package com.pkaushik.safeHome.validation.impl;

import com.pkaushik.safeHome.exceptions.DateConventionException;
import com.pkaushik.safeHome.exceptions.ScheduleDateTimeException;
import com.pkaushik.safeHome.model.DateTime;
import com.pkaushik.safeHome.model.Schedule;
import com.pkaushik.safeHome.validation.DateTimeValidationIF;

import java.util.Calendar;
import java.util.Date;

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


}
