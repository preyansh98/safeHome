package com.pkaushik.safeHome.service;

import com.pkaushik.safeHome.model.Walker;

import java.util.List;

public interface StudentServiceIF {
    List<Walker> getAllPotentialWalkers(int mcgillID);
}
