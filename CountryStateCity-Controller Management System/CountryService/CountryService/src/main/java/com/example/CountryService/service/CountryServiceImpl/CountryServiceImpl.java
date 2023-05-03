package com.example.CountryService.service.CountryServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.CountryService.beans.AddResponse;
import com.example.CountryService.beans.City;
import com.example.CountryService.beans.Country;
import com.example.CountryService.beans.State;
import com.example.CountryService.repository.CountryRepository;
import com.example.CountryService.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
    // public static HashMap<Integer,Country> countryIdMap;

    // public CountryServiceImpl(){
    //     countryIdMap = new HashMap<Integer,Country>();

    //     Country indiaCountry = new Country(1, "India", "Delhi");
    //     Country usaCountry = new Country(2, "USA", "Washington");
    //     Country ukCountry = new Country(3, "UK", "London");

    //     countryIdMap.put(1,indiaCountry);
    //     countryIdMap.put(2,usaCountry);
    //     countryIdMap.put(3,ukCountry);
    // }

    @Autowired
    private CountryRepository countryRepository;

    private Logger logger = LoggerFactory.getLogger(CountryService.class);
    
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryById(int countryid) {
        Country country = countryRepository.findBycountryId(countryid);
        ArrayList<State> forObject = restTemplate.getForObject("http://STATE-SERVICE/state/getStateByCountryIdCity/"+country.getcountryId(),ArrayList.class);
        logger.info("{} ",forObject);
        country.setStates(forObject);
        return country;
    }

    @Override
    public Country getCountryByName(String countryname) {
        // Country country = null;
        // for(int i:countryIdMap.keySet()){
        //     if(countryIdMap.get(i).getCountryName().equals(name)){
        //         country = countryIdMap.get(i);
        //     }
        // }
        //     return country;
        List<Country> countries = countryRepository.findAll();
        for(Country c: countries){
            if(c.getCountryName().equals(countryname)){
                return c;
            }
        }
        return null;
    }

    @Override
    public Country addCountry(Country country) {
        country.setcountryId(getMaxId());
        // countryIdMap.put(country.getId(), country);
        countryRepository.save(country);
        return country;

    }

    @Override
    public Country updateCountry(Country country) {
                // if(country.getId()>0){
                //     countryIdMap.put(country.getId(), country);
                // }
                countryRepository.save(country);
                return country;
    }

    @Override
    public AddResponse deleteCountry(int id) {
        // countryIdMap.remove(id);
        countryRepository.deleteById(id);
        AddResponse res = new AddResponse();
        res.setMsg("Country Deleted");
        res.setId(id);
        return res;
    }

    public int getMaxId(){
        // int max = 0;
        // for(int i:countryIdMap.keySet()){
        //     if(max<=i){
        //         max = i;
        //     }
        //     }
        //     return max+1;
        return countryRepository.findAll().size()+1;
    }



    }

