package coronavirus.tracker.core.service;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.core.converter.StateEntities2StatesConverter;
import coronavirus.tracker.core.converter.StateEntity2StateConverter;
import coronavirus.tracker.core.dto.StateDTO;
import coronavirus.tracker.entitycommon.entity.Country;
import coronavirus.tracker.entitycommon.entity.State;
import coronavirus.tracker.core.mapper.DTOToEntityMapper;
import coronavirus.tracker.core.repository.CountryRepository;
import coronavirus.tracker.core.repository.StateRepository;
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
    private final StateEntity2StateConverter stateEntity2StateConverter;
    private final StateEntities2StatesConverter stateEntities2StatesConverter;

    public CoronavirusTrackerCoreProtos.States findStatesByCountryName(String countryName) {
        List<State> stateEntities = stateRepository.findStatesByCountry_CountryName(countryName);
        return stateEntities2StatesConverter.convert(stateEntities);
    }

    public CoronavirusTrackerCoreProtos.State findOneByCountryNameAndStateName(String countryName, String stateName) {
        State stateEntity = stateRepository.findOneByCountry_CountryNameAndStateName(countryName, stateName).orElseThrow(
                () -> new IllegalArgumentException("State named " +
                        stateName + " in country named " + countryName + " was not found"));
        return stateEntity2StateConverter.convert(stateEntity);
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
