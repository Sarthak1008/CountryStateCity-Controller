package com.example.CountryService.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CountryService.beans.Country;
import com.example.CountryService.service.CountryService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/country")
public class CountryController {
    
    @Autowired
    CountryService countryService;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(CountryController.class);

    @GetMapping(value="/getCountries")
    public List<Country> getAllCountry() {
        return countryService.getAllCountries();
    }
    int retryCount = 1;

    @GetMapping(value="/getCountries/{id}")
    @CircuitBreaker(name="StateBreaker",fallbackMethod = "StateFallback")
    @Retry(name="StateBreaker",fallbackMethod = "StateFallback")
    @RateLimiter(name = "StateBreaker",fallbackMethod = "StateFallback")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") int countryId) {

            Country country = countryService.getCountryById(countryId);
            return new ResponseEntity<Country> (country,HttpStatus.OK);
    }
    public ResponseEntity<Country> StateFallback(int countryId,Exception ex){
        logger.info("Fallback is executed because service is down: ",ex.getMessage());
        logger.info("Retry Count: {} ",retryCount);
        retryCount++;
        Country country = Country.builder().countryId(0).countryName("dummmy").countryCapital("dummyCapital").build();
        return new ResponseEntity<Country>(country,HttpStatus.OK);
    }


    @GetMapping(value="/getCountries/countryname")
    public ResponseEntity<Country> getCountryByName(@RequestParam("name") String countryname) {
        try {
            Country country = countryService.getCountryByName(countryname);
            return new ResponseEntity<Country> (country,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Country> (HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "/addCountry")
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }

    @PutMapping(value="/updateCountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id,@RequestBody Country country) {
        try {
            Country existCountry=countryService.getCountryById(id);
            existCountry.setCountryName(country.getCountryName());
            existCountry.setCountryCapital(country.getCountryCapital());

            Country updatedCountry = countryService.updateCountry(existCountry);
            return new ResponseEntity<Country> (updatedCountry,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Country> (HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value="/deleteCountry/{id}")
    public String deleteCountry(@PathVariable("id") int id) {
        String cname = countryService.getCountryById(id).getCountryName();
        countryService.deleteCountry(id);
        return ("Deleted Country "+cname);
    }
}
