package com.example.CityService.services;

import java.util.List;

import com.example.CityService.entities.AddResponse;
import com.example.CityService.entities.City;


public interface CityService {

    List<City> getAllCities();
    List<City> findByStateId(int stateId);
    City getCityById(int id);
    City getCityByName(String city);
    City addCity(City city);
    City updateCity(City city);
    AddResponse deleteCity(int stateId);
}
