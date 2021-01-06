package com.ct.dataapi.service;

import com.ct.dataapi.dto.CountryDTO;
import com.ct.dataapi.mapper.DTOToEntityMapper;
import com.ct.dataapi.repository.CountryRepository;
import com.ct.entitycommon.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final DTOToEntityMapper DTOToEntityMapper;

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    public Country findOneCountryByName(String countryName) {
        return countryRepository.findOneByCountryName(countryName).orElse(null);
    }

    public Country saveCountry(CountryDTO countryDTO) {
        Country countryEntity =
                new Country(countryDTO.getCountryName(), countryDTO.getLatitude(), countryDTO.getLongitude());
        return countryRepository.save(countryEntity);
    }

    public Country updateCountry(String countryName, CountryDTO countryDTO) {
        countryDTO.setCountryName(countryName);
        Country country;
        Optional<Country> countryFromRepoOpt = countryRepository.findOneByCountryName(countryName);
        if (countryFromRepoOpt.isPresent()) {
            country = countryFromRepoOpt.get();
            country.setLatitude(countryDTO.getLatitude());
            country.setLongitude(countryDTO.getLongitude());
        } else {
            country = DTOToEntityMapper.mapCountryDTO(countryDTO);
        }
        return countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(String countryName) {
        countryRepository.deleteByCountryName(countryName);
    }
}
