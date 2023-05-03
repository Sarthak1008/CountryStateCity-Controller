package com.example.StateService.services;
import java.util.List;

import com.example.StateService.entities.AddResponse;
import com.example.StateService.entities.State;

public interface StateService {

    List<State> getAllStates();
    List<State> getByCountryId(int countryId);
    State getStateById(int id);
    State getStatesListById(int id);
    State getStateByName(String stateName);
    State addState(State state);
    State updateState(State state);
    AddResponse deleteState(int stateId);
    List<State> getAllCity(int countryId);

}
