package com.pkaushik.safeHome.repository;

import java.util.Optional;

import com.pkaushik.safeHome.model.SafeHomeUser;

import org.springframework.data.repository.CrudRepository;

public interface SafeHomeUserRepository extends CrudRepository<SafeHomeUser, Integer> {

    //TODO; get associated roles.

}