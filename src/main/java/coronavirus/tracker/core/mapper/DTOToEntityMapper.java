package coronavirus.tracker.core.mapper;

import coronavirus.tracker.core.dto.CasesPerDateDTO;
import coronavirus.tracker.core.dto.CountryDTO;
import coronavirus.tracker.core.dto.StateDTO;
import coronavirus.tracker.entitycommon.entity.Country;
import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import coronavirus.tracker.entitycommon.entity.State;
import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
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
