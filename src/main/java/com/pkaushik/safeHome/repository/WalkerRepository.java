package com.pkaushik.safeHome.repository;

import java.util.Optional;

import com.pkaushik.safeHome.model.Walker;

import org.springframework.data.repository.CrudRepository;

public interface WalkerRepository extends CrudRepository<Walker, Integer> {

    //TODO: depending on data model, might have to override findById to take in user id and return walker mapped to that user id.
}