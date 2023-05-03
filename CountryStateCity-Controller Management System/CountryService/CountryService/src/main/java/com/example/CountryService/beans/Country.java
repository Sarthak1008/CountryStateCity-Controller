package com.example.CountryService.beans;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name="country")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @Column(name="countryId")
    private int countryId;

    @Column(name="countryName")
    private String countryName;

    @Column(name="countryCapital")
    private String countryCapital;
    
    
    @Override
    public String toString() {
        return "Country [countryId=" + countryId + ", countryName=" + countryName + ", countryCapital=" + countryCapital + "]";
    }
    public int getcountryId() {
        return countryId;
    }
    public void setcountryId(int countryId) {
        this.countryId = countryId;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCountryCapital() {
        return countryCapital;
    }
    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    @jakarta.persistence.Transient
    private List<State> states = new ArrayList<>();

    public List<State> getStates() {
        return states;
    }
    public void setStates(List<State> states) {
        this.states = states;
    }
    
}
