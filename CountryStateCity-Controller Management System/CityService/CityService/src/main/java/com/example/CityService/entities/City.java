package com.example.CityService.entities;

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
@Table(name="city")
public class City {

    @Id
    @Column(name="cityId")
    private int cityId;

    @Column(name="stateId")
    private int stateId;
    
    @Column(name="cityName")
    private String cityName;

    @Column(name="countryId")
    private int countryId;
}