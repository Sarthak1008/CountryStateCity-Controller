package com.example.StateService.entities;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="state")
public class State {

    @Id
    @Column(name="stateId")
    private int stateId;
    
    @Column(name="stateName")
    private String stateName;

    @Column(name="countryId")
    private int countryId;

    @jakarta.persistence.Transient
    private List<City> cities = new ArrayList<>();
}
