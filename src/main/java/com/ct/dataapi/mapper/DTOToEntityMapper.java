package com.ct.dataapi.mapper;

import com.ct.dataapi.dto.CasesPerDateDTO;
import com.ct.dataapi.dto.CountryDTO;
import com.ct.dataapi.dto.StateDTO;
import com.ct.dataapi.entitiestodelete.Country;
import com.ct.dataapi.entitiestodelete.CountryCasesPerDate;
import com.ct.dataapi.entitiestodelete.State;
import com.ct.dataapi.entitiestodelete.StateCasesPerDate;
import org.springframework.stereotype.Component;

@Component
public class DTOToEntityMapper {

    public Country mapCountryDTO(CountryDTO countryDTO) {
        return new Country(countryDTO.getCountryName(), countryDTO.getLatitude(), countryDTO.getLongitude());
    }

    public State mapStateDTO(Country country, StateDTO stateDTO) {
        return new State(stateDTO.getStateName(), stateDTO.getLatitude(), stateDTO.getLongitude(), country);
    }

    public CountryCasesPerDate mapCountryCasesPerDateDTO(Country country,
                                                         CasesPerDateDTO casesPerDateDTO) {
        return new CountryCasesPerDate(
                country, casesPerDateDTO.getDate(), casesPerDateDTO.getNumberOfCases());
    }

    public StateCasesPerDate mapStateCasesPerDateDTO(State state, CasesPerDateDTO casesPerDateDTO) {
        return new StateCasesPerDate(state, casesPerDateDTO.getDate(), casesPerDateDTO.getNumberOfCases());
    }
}
