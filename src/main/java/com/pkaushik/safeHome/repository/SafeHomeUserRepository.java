package com.pkaushik.safeHome.repository;

import com.pkaushik.safeHome.model.SafeHomeUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafeHomeUserRepository extends JpaRepository<SafeHomeUser, Integer> {

    //TODO; get associated roles.

}