package com.example.CountryService.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CountryService.beans.Country;

public interface CountryRepository extends JpaRepository<Country,Integer> {
   Country findBycountryId(int id);
}
