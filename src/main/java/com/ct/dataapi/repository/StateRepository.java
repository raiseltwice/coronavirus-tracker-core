package com.ct.dataapi.repository;

import com.ct.dataapi.entitiestodelete.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    List<State> findStatesByCountry_CountryName(String countryName);
    Optional<State> findOneByCountry_CountryNameAndStateName(String countryName, String stateName);

    void deleteByCountry_CountryNameAndStateName(String countryName, String stateName);
}
