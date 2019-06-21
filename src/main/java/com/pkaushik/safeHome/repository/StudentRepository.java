package com.pkaushik.safeHome.repository;

import com.pkaushik.safeHome.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer>{

}