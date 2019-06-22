package com.pkaushik.safeHome.repository;

import com.pkaushik.safeHome.model.SpecificRequest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<SpecificRequest, Integer>{

}