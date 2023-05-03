package com.example.CityService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CityService.entities.City;

public interface CityRepository extends JpaRepository<City,Integer>{
    City findBycityId(int id);
    List<City> findBystateId(int stateId);;
}
