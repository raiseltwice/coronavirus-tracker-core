package com.ct.dataapi.service;

import com.ct.dataapi.dto.CasesPerDateDTO;
import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.CountryCasesPerDate;
import com.ct.dataapi.mapper.DTOToEntityMapper;
import com.ct.dataapi.repository.CountryCasesPerDateRepository;
import com.ct.dataapi.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CountryCasesPerDateService {

    private final CountryCasesPerDateRepository countryCasesPerDateRepository;
    private final CountryRepository countryRepository;
    private final DTOToEntityMapper dtoToEntityMapper;

    public List<CountryCasesPerDate> findAllByCountryName(String countryName) {
        return countryCasesPerDateRepository.findAllByCountry_CountryName(countryName);
    }

    public CountryCasesPerDate findOneByCountryNameAndDate(String countryName, LocalDate date) {
        return countryCasesPerDateRepository.findOneByCountry_CountryNameAndDate(countryName, date).orElse(null);
    }

    public List<CountryCasesPerDate> findAllByCountryNameAndDateRange(String countryName,
                                                                      LocalDate startDate,
                                                                      LocalDate endDate) {
        return countryCasesPerDateRepository.findAllByCountry_CountryNameAndDateBetween(
                countryName, startDate, endDate);
    }

    public CountryCasesPerDate createCountryCasesPerDate(String countryName,
                                                         CasesPerDateDTO casesPerDateDTO) {
        CountryCasesPerDate countryCasesPerDate = constructCasesPerDateEntity(countryName, casesPerDateDTO);
        return countryCasesPerDateRepository.save(countryCasesPerDate);
    }

    public CountryCasesPerDate updateCountryCasesPerDate(String countryName,
                                                         LocalDate date,
                                                         CasesPerDateDTO casesPerDateDTO) {
        casesPerDateDTO.setDate(date);
        CountryCasesPerDate countryCasesPerDate;
        Optional<CountryCasesPerDate> countryCasesPerDateFromRepoOpt =
                countryCasesPerDateRepository.findOneByCountry_CountryNameAndDate(countryName,
                        casesPerDateDTO.getDate());
        if (countryCasesPerDateFromRepoOpt.isPresent()) {
            countryCasesPerDate = countryCasesPerDateFromRepoOpt.get();
            countryCasesPerDate.setNumberOfCases(casesPerDateDTO.getNumberOfCases());
        } else {
            countryCasesPerDate = constructCasesPerDateEntity(countryName, casesPerDateDTO);
        }
        return countryCasesPerDateRepository.save(countryCasesPerDate);
    }

    public void deleteCountryCasesPerDate(String countryName, LocalDate date) {
        countryCasesPerDateRepository.deleteByCountry_CountryNameAndDate(countryName, date);
    }

    private CountryCasesPerDate constructCasesPerDateEntity(String countryName,
                                                            CasesPerDateDTO casesPerDateDTO) {
        Country countryFromRepo = fetchCountry(countryName);
        return dtoToEntityMapper.mapCountryCasesPerDateDTO(countryFromRepo, casesPerDateDTO);
    }

    private Country fetchCountry(String countryName) {
        Optional<Country> countryFromRepoOpt = countryRepository.findOneByCountryName(countryName);
        return countryFromRepoOpt.orElseThrow(() ->
                new IllegalArgumentException("Country " + countryName + " doesn't exist"));
    }
}
