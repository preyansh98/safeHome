package com.pkaushik.safeHome.service;

import java.util.UUID;

public interface WalkerServiceIF {

    void setWalkerScheduleService(int mcgillID, int startDay, int startMonth, int startYear,
                                  int endDay, int endMonth, int endYear, int startHour, int startMin, int endHour, int endMin);

    void changeWalkerScheduleService(int mcgillID, int startDay, int startMonth, int startYear,
                              int endDay, int endMonth, int endYear, int startHour, int startMin, int endHour, int endMin);


    void acceptAssignmentService(int mcgillID, UUID assignmentID);

    void refuseAssignmentService(int mcgillID, UUID assignmentID);


}
