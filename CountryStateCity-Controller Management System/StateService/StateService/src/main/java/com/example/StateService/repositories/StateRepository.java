package com.example.StateService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StateService.entities.State;

public interface StateRepository extends JpaRepository<State,Integer>{
    State findByStateId(int stateId);
    List<State> findByCountryId(int countryId);
}
