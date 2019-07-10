package com.pkaushik.safeHome.repository;

import java.util.Optional;

import com.pkaushik.safeHome.model.Walker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkerRepository extends JpaRepository<Walker, Integer> {

    //TODO: depending on data model, might have to override findById to take in user id and return walker mapped to that user id.
}