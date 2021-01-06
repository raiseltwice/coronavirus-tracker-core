package com.ct.dataapi.service;

import com.ct.dataapi.entitiestodelete.Country;
import com.ct.dataapi.entitiestodelete.State;
import com.ct.dataapi.mapper.EntityToProtobufMapper;
import com.ct.dataapi.repository.CountryCasesPerDateRepository;
import com.ct.dataapi.repository.CountryRepository;
import com.ct.dataapi.repository.StateCasesPerDateRepository;
import com.ct.dataapi.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CountryCasesPerDateRepository countryCasesPerDateRepository;
    private final StateCasesPerDateRepository stateCasesPerDateRepository;
    private final EntityToProtobufMapper entityToProtobufMapper;

//    public Entity.Data findAllCountryCasesPerDateProtobuf() {
//        List<Country> data = countryRepository.findAll();
//        return null;
////        return entityToProtobufMapper.map();
//    }

    public List<State> findStatesByCountryName(String countryName) {
        return stateRepository.findStatesByCountry_CountryName(countryName);
    }

    public List<Country> findCasesBetweenDates(LocalDate start, LocalDate end) {
        return countryRepository.findAll();
    }

    public List<Country> findByLatitudeRange(Double latitudeFrom, Double latitudeTo) {
        return countryRepository.findByLatitudeBetween(latitudeFrom, latitudeTo);
    }
}
