package com.ct.dataapi.service;

import com.ct.dataapi.dto.StateDTO;
import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.State;
import com.ct.dataapi.mapper.DTOToEntityMapper;
import com.ct.dataapi.repository.CountryRepository;
import com.ct.dataapi.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final DTOToEntityMapper dtoToEntityMapper;

    public List<State> findStatesByCountryName(String countryName) {
        return stateRepository.findStatesByCountry_CountryName(countryName);
    }

    public State findOneByCountryNameAndStateName(String countryName, String stateName) {
        return stateRepository.findOneByCountry_CountryNameAndStateName(countryName, stateName).orElse(null);
    }

    public State createState(String countryName, StateDTO stateDTO) {
        State state = constructStateEntity(countryName, stateDTO);
        return stateRepository.save(state);
    }

    public State updateState(String countryName, String stateName, StateDTO stateDTO) {
        stateDTO.setStateName(stateName);
        State state;
        Optional<State> stateFromRepoOpt =
                stateRepository.findOneByCountry_CountryNameAndStateName(countryName, stateDTO.getStateName());
        if (stateFromRepoOpt.isPresent()) {
            state = stateFromRepoOpt.get();
            state.setLatitude(stateDTO.getLatitude());
            state.setLongitude(stateDTO.getLongitude());
        } else {
            state = constructStateEntity(countryName, stateDTO);
        }
        return stateRepository.save(state);
    }

    public void deleteState(String countryName, String stateName) {
        stateRepository.deleteByCountry_CountryNameAndStateName(countryName, stateName);
    }

    private State constructStateEntity(String countryName, StateDTO stateDTO) {
        Country countryFromRepo = fetchCountry(countryName);
        return dtoToEntityMapper.mapStateDTO(countryFromRepo, stateDTO);
    }

    private Country fetchCountry(String countryName) {
        Optional<Country> countryFromRepoOpt = countryRepository.findOneByCountryName(countryName);
        return countryFromRepoOpt.orElseThrow(() ->
                new IllegalArgumentException("Country " + countryName + " doesn't exist"));
    }
}
