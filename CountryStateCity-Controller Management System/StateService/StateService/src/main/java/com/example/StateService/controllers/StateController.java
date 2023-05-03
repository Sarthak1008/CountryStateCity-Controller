package com.example.StateService.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
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

import com.example.StateService.entities.State;
import com.example.StateService.services.StateService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    private StateService stateService;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(StateController.class);

    @GetMapping(value="/getStates")
    public List<State> getAllStates() {
        return stateService.getAllStates();
    }
    int retryCount = 1;

    @GetMapping(value="/getStatesById/{id}")
    @CircuitBreaker(name="CityBreaker",fallbackMethod = "CityFallback")
    @Retry(name="CityBreaker",fallbackMethod = "CityFallback")
    @RateLimiter(name = "CityBreaker",fallbackMethod = "CityFallback")
    public ResponseEntity<State> getStateById(@PathVariable("id") int stateId) {
            logger.info("Get Single User Handler: StateController");
            State state = stateService.getStateById(stateId);
            logger.info("Retry Count: {} ",retryCount);
            retryCount++;
            return new ResponseEntity<State> (state,HttpStatus.OK);
    }

    public ResponseEntity<State> CityFallback(int stateId,Exception ex){
        logger.info("Fallback is executed because service is down: ",ex.getMessage());
        State state = State.builder().stateId(0).stateName("dummy").countryId(0).build();
        return new ResponseEntity<State>(state,HttpStatus.OK);
    }

    @GetMapping(value="/getStateListById/{id}")
    public ResponseEntity<State> getStateListById(@PathVariable("id") int stateId) {
        try {
            State state = stateService.getStatesListById(stateId);
            return new ResponseEntity<State> (state,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<State> (HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value="/getStateByCountryId/{id}")
    public ResponseEntity<List<State>> getCityByStateId(@PathVariable("id") int countryId) {
        try {
            List<State> city = stateService.getByCountryId(countryId);
            return new ResponseEntity<List<State>> (city,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<List<State>> (HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value="/getStateByCountryIdCity/{countryid}")
    public ResponseEntity<List<State>> getCityByCountry(@PathVariable("countryid") int countryId) {
        
            List<State> city = stateService.getAllCity(countryId);
            return new ResponseEntity<List<State>> (city,HttpStatus.OK);
    }
    @GetMapping(value="/getStates/statename")
    public ResponseEntity<State> getStateByName(@RequestParam("name") String statename) {
        try {
            State state = stateService.getStateByName(statename);
            return new ResponseEntity<State> (state,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<State> (HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "/addStates")
    public State addState(@RequestBody State state){
        return stateService.addState(state);
    }

    @PutMapping(value="/updateState/{id}")
    public ResponseEntity<State> updateState(@PathVariable(value = "id") int id,@RequestBody State state) {
        try {
            State existState=stateService.getStateById(id);
            existState.setStateName(state.getStateName());
            existState.setCountryId(state.getCountryId());

            State updatedState = stateService.updateState(existState);
            return new ResponseEntity<State> (updatedState,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<State> (HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value="/deleteState/{id}")
    public String deleteState(@PathVariable("id") int stateId) {
        String cname = stateService.getStateById(stateId).getStateName();
        stateService.deleteState(stateId);
        return ("Deleted State "+cname);
    }
}

