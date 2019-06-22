package com.pkaushik.safeHome.repository;

import com.pkaushik.safeHome.model.Assignment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, Integer>{

}