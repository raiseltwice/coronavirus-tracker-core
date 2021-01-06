package com.ct.dataapi.repository;

import com.ct.dataapi.entitiestodelete.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findByLatitudeBetween(Double latitudeFrom, Double latitudeTo);
    Optional<Country> findOneByCountryName(String countryName);

    void deleteByCountryName(String countryName);
}
