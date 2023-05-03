package com.example.CityService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CityService.entities.City;
import com.example.CityService.services.CityService;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping(value="/getcities")
    public List<City> getAllCountry() {
        return cityService.getAllCities();
    }

    @GetMapping(value="/getCityById/{id}")
    public ResponseEntity<City> getCountryById(@PathVariable("id") int cityId) {
        try {
            City city = cityService.getCityById(cityId);
            return new ResponseEntity<City> (city,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<City> (HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value="/getCityByStateId/{id}")
    public ResponseEntity<List<City>> getCityByStateId(@PathVariable("id") int stateId) {
        try {
            List<City> city = cityService.findByStateId(stateId);
            return new ResponseEntity<List<City>> (city,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<List<City>> (HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value="/getCity/cityname")
    public ResponseEntity<City> getCountryByName(@RequestParam("name") String statename) {
        try {
            City city = cityService.getCityByName(statename);
            return new ResponseEntity<City> (city,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<City> (HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "/addCity")
    public City addCountry(@RequestBody City city){
        return cityService.addCity(city);
    }

    @PutMapping(value="/updateCity/{id}")
    public ResponseEntity<City> updateCountry(@PathVariable(value = "id") int id,@RequestBody City city) {
        try {
            City existCity=cityService.getCityById(id);
            existCity.setCityName(city.getCityName());
            existCity.setCountryId(city.getCountryId());
            existCity.setStateId(city.getStateId());


            City updatedCity = cityService.updateCity(existCity);
            return new ResponseEntity<City> (updatedCity,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<City> (HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value="/deleteCity/{id}")
    public String deleteCountry(@PathVariable("id") int cityId) {
        String cname = cityService.getCityById(cityId).getCityName();
        cityService.deleteCity(cityId);
        return ("Deleted City "+cname);
    }
}

