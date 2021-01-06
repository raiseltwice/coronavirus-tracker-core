package com.ct.dataapi.service;

import com.ct.dataapi.dto.CasesPerDateDTO;
import com.ct.entitycommon.entity.State;
import com.ct.entitycommon.entity.StateCasesPerDate;
import com.ct.dataapi.mapper.DTOToEntityMapper;
import com.ct.dataapi.repository.CountryRepository;
import com.ct.dataapi.repository.StateCasesPerDateRepository;
import com.ct.dataapi.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class StateCasesPerDateService {

    private final StateCasesPerDateRepository stateCasesPerDateRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final DTOToEntityMapper dtoToEntityMapper;

    public List<StateCasesPerDate> findAllByCountryNameAndStateName(String countryName, String stateName) {
        return stateCasesPerDateRepository.findAllByState_Country_CountryNameAndState_StateName(countryName, stateName);
    }

    public StateCasesPerDate findOneByCountryNameAndStateNameAndDate(String countryName, String stateName, LocalDate date) {
        return stateCasesPerDateRepository.findOneByState_Country_CountryNameAndState_StateNameAndDate(
                countryName, stateName, date).orElse(null);
    }

    public List<StateCasesPerDate> findAllByCountryNameAndStateNameAndDateRange(String countryName,
                                                                                String stateName,
                                                                                LocalDate startDate,
                                                                                LocalDate endDate) {
        return stateCasesPerDateRepository.findAllByState_Country_CountryNameAndState_StateNameAndDateBetween(
                countryName, stateName, startDate, endDate);
    }

    public StateCasesPerDate createStateCasesPerDate(String countryName, String stateName, CasesPerDateDTO casesPerDateDTO) {
        StateCasesPerDate stateCasesPerDate = constructStatePerDateEntity(countryName, stateName, casesPerDateDTO);
        return stateCasesPerDateRepository.save(stateCasesPerDate);
    }

    public StateCasesPerDate updateStateCasesPerDate(String countryName,
                                                     String stateName,
                                                     LocalDate date,
                                                     CasesPerDateDTO casesPerDateDTO) {
        casesPerDateDTO.setDate(date);
        StateCasesPerDate stateCasesPerDate;
        Optional<StateCasesPerDate> stateCasesPerDateFromRepoOpt =
                stateCasesPerDateRepository.findOneByState_Country_CountryNameAndState_StateNameAndDate(
                        countryName, stateName, date);
        if (stateCasesPerDateFromRepoOpt.isPresent()) {
            stateCasesPerDate = stateCasesPerDateFromRepoOpt.get();
            stateCasesPerDate.setNumberOfCases(casesPerDateDTO.getNumberOfCases());
        } else {
            stateCasesPerDate = constructStatePerDateEntity(countryName, stateName, casesPerDateDTO);
        }
        return stateCasesPerDateRepository.save(stateCasesPerDate);
    }

    public void deleteStateCasesPerDate(String countryName, String stateName, LocalDate date) {
        stateCasesPerDateRepository.deleteByState_Country_CountryNameAndState_StateNameAndDate(countryName, stateName, date);
    }

    private StateCasesPerDate constructStatePerDateEntity(String countryName,
                                                          String stateName,
                                                          CasesPerDateDTO casesPerDateDTO) {
        State stateFromRepo = fetchState(countryName, stateName);
        return dtoToEntityMapper.mapStateCasesPerDateDTO(stateFromRepo, casesPerDateDTO);
    }

    private State fetchState(String countryName, String stateName) {
        Optional<State> stateFromRepoOpt =
                stateRepository.findOneByCountry_CountryNameAndStateName(countryName, stateName);
        return stateFromRepoOpt.orElseThrow(() ->
                new IllegalArgumentException("State " + stateName + " doesn't exist"));
    }
}
