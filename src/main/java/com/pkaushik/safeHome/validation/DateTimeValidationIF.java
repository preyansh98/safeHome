package com.pkaushik.safeHome.validation;

import java.util.Calendar;

public interface DateTimeValidationIF {


    void validateDayConvention(int day);

    void validateMonthConvention(int month);

    void validateYearConvention(int year);

    void validateHourConvention(int hour);

    void validateMinConvention(int min);

    void validateStartBeforeEnd(int startDay, int startMonth, int startYear,
                                    int endDay, int endMonth, int endYear, int startHour, int startMin,
                                        int endHour, int endMin);
}
