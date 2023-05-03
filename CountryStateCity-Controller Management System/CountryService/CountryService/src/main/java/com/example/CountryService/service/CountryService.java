package com.example.CountryService.service;

import java.util.List;

import com.example.CountryService.beans.AddResponse;
import com.example.CountryService.beans.Country;

public interface CountryService {
    List<Country> getAllCountries();
    Country getCountryById(int id);
    Country getCountryByName(String name);
    Country addCountry(Country country);
    Country updateCountry(Country country);
    AddResponse deleteCountry(int id);
}
