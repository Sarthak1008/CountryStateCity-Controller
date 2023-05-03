package com.example.CityService.services.CityServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CityService.entities.AddResponse;
import com.example.CityService.entities.City;
import com.example.CityService.repositories.CityRepository;
import com.example.CityService.services.CityService;

@Service
public class CityServicesImpl implements CityService{

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCityById(int id) {
        return cityRepository.findBycityId(id);
    }

    @Override
    public City getCityByName(String city) {
        List<City> cities = cityRepository.findAll();
        for(City c:cities){
            if(c.getCityName().equals(city)){
                return c;
            }
        }
        return null;
    }
    

    @Override
    public City addCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City updateCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public AddResponse deleteCity(int stateId) {
        cityRepository.deleteById(stateId);
        AddResponse res = new AddResponse();
        res.setMsg("State Deleted");
        res.setId(stateId);
        return res;
    }

    @Override
    public List<City> findByStateId(int stateId) {
        return cityRepository.findBystateId(stateId);
    }
    
}
