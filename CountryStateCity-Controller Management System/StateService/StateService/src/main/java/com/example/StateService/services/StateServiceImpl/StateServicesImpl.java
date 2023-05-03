package com.example.StateService.services.StateServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.StateService.entities.AddResponse;
import com.example.StateService.entities.City;
import com.example.StateService.entities.State;
import com.example.StateService.repositories.StateRepository;
import com.example.StateService.services.StateService;

@Service
public class StateServicesImpl implements StateService{

    @Autowired
    private StateRepository stateRepository;

    private Logger logger = LoggerFactory.getLogger(StateService.class);
    
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @Override
    public State getStateById(int stateId) {
        State state = stateRepository.findByStateId(stateId);
        ArrayList<City> forObject = restTemplate.getForObject("http://CITY-SERVICE/city/getCityByStateId/"+state.getStateId(),ArrayList.class);
        logger.info("{} ",forObject);
        state.setCities(forObject);
        return state;
    }
    @Override
    public List<State> getAllCity(int countryId){
        List<State> state=stateRepository.findAll();
        ArrayList<State> stateInCountry=new ArrayList<>();
        for(State s:state){
            if(s.getCountryId()==countryId){
                ArrayList<City> cities = restTemplate.getForObject("http://CITY-SERVICE/city/getCityByStateId/"+s.getStateId(),ArrayList.class);
                s.setCities(cities);
                stateInCountry.add(s);
            }
        }
          return stateInCountry;
    }
    

    @Override
    public State getStateByName(String stateName) {
        List<State> states = stateRepository.findAll();
        for(State s:states){
            if(s.getStateName().equals(stateName)){
                return s;
            }
        }
        return null;
    }

    @Override
    public State addState(State state) {
        return stateRepository.save(state);
    }

    @Override
    public State updateState(State state) {
        return stateRepository.save(state);
    }

    @Override
    public AddResponse deleteState(int stateId) {
        stateRepository.deleteById(stateId);
        AddResponse res = new AddResponse();
        res.setMsg("State Deleted");
        res.setId(stateId);
        return res;
    }

    public int getMaxId(){
        return stateRepository.findAll().size()+1;
    
}

    @Override
    public List<State> getByCountryId(int countryId) {
        return stateRepository.findByCountryId(countryId);
    }

    @Override
    public State getStatesListById(int stateId) {
        return stateRepository.findByStateId(stateId);
    }
}
