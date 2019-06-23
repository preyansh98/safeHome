package com.pkaushik.safeHome.repository;

import com.pkaushik.safeHome.model.Assignment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, UUID>{

}