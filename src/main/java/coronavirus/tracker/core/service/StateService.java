package coronavirus.tracker.core.service;

import coronavirus.tracker.core.dto.StateData;
import coronavirus.tracker.core.repository.CountryRepository;
import coronavirus.tracker.core.repository.StateRepository;
import coronavirus.tracker.entitycommon.entity.Country;
import coronavirus.tracker.entitycommon.entity.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.StateDTO;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.StatesDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.StateEntities2StatesConverter;
import static coronavirus.tracker.core.utils.ConverterQualifiers.StateEntity2StateConverter;

@Transactional
@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    @Qualifier(StateEntity2StateConverter)
    private final ConversionService stateEntity2StateConverter;
    @Qualifier(StateEntities2StatesConverter)
    private final ConversionService stateEntities2StatesConverter;

    public StatesDTO findStatesByCountryName(String countryName) {
        List<State> stateEntities = stateRepository.findStatesByCountry_CountryName(countryName);
        return stateEntities2StatesConverter.convert(stateEntities, StatesDTO.class);
    }

    public StateDTO findOneByCountryNameAndStateName(String countryName, String stateName) {
        State stateEntity = stateRepository.findOneByCountry_CountryNameAndStateName(countryName, stateName)
                .orElseThrow(() -> new IllegalArgumentException("State named " +
                        stateName + " in country named " + countryName + " was not found"));
        return stateEntity2StateConverter.convert(stateEntity, StateDTO.class);
    }

    public State createState(String countryName, StateData stateData) {
        State state = constructStateEntity(countryName, stateData);
        return stateRepository.save(state);
    }

    public State updateState(String countryName, String stateName, StateData stateData) {
        stateData.setStateName(stateName);
        State state;
        Optional<State> stateFromRepoOpt =
                stateRepository.findOneByCountry_CountryNameAndStateName(countryName, stateData.getStateName());
        if (stateFromRepoOpt.isPresent()) {
            state = stateFromRepoOpt.get();
            state.setLatitude(stateData.getLatitude());
            state.setLongitude(stateData.getLongitude());
        } else {
            state = constructStateEntity(countryName, stateData);
        }
        return stateRepository.save(state);
    }

    public void deleteState(String countryName, String stateName) {
        stateRepository.deleteByCountry_CountryNameAndStateName(countryName, stateName);
    }

    private State constructStateEntity(String countryName, StateData stateData) {
        Country countryFromRepo = fetchCountry(countryName);
        return new State(stateData.getStateName(), stateData.getLatitude(), stateData.getLongitude(), countryFromRepo);
    }

    private Country fetchCountry(String countryName) {
        Optional<Country> countryFromRepoOpt = countryRepository.findOneByCountryName(countryName);
        return countryFromRepoOpt.orElseThrow(() ->
                new IllegalArgumentException("Country " + countryName + " doesn't exist"));
    }
}
