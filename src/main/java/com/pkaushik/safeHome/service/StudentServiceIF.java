package com.pkaushik.safeHome.service;

import com.pkaushik.safeHome.model.Walker;

import java.util.List;

public interface StudentServiceIF {

    void selectWalkerForRequestService(int studentID, int walkerID);

    List<Walker> getAllPotentialWalkers(int mcgillID);
}
