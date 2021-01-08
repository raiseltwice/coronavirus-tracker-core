package coronavirus.tracker.core.service;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.core.converter.StateCasesPerDateEntities2CasesPerDateCollectionConverter;
import coronavirus.tracker.core.converter.StateCasesPerDateEntity2CasesPerDateConverter;
import coronavirus.tracker.core.dto.CasesPerDateDTO;
import coronavirus.tracker.entitycommon.entity.State;
import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
import coronavirus.tracker.core.mapper.DTOToEntityMapper;
import coronavirus.tracker.core.repository.StateCasesPerDateRepository;
import coronavirus.tracker.core.repository.StateRepository;
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
    private final StateRepository stateRepository;
    private final DTOToEntityMapper dtoToEntityMapper;
    private final StateCasesPerDateEntity2CasesPerDateConverter stateCasesPerDateEntity2CasesPerDateConverter;
    private final StateCasesPerDateEntities2CasesPerDateCollectionConverter stateCasesPerDateEntities2CasesPerDateCollectionConverter;


    public CoronavirusTrackerCoreProtos.CasesPerDateCollection findAllByCountryNameAndStateName(String countryName, String stateName) {
        List<StateCasesPerDate> stateCasesPerDateCollection = stateCasesPerDateRepository.findAllByState_Country_CountryNameAndState_StateName(countryName, stateName);
        return stateCasesPerDateEntities2CasesPerDateCollectionConverter.convert(stateCasesPerDateCollection);
    }

    public CoronavirusTrackerCoreProtos.CasesPerDate findOneByCountryNameAndStateNameAndDate(String countryName, String stateName, LocalDate date) {
        StateCasesPerDate stateCasesPerDateEntity = stateCasesPerDateRepository.findOneByState_Country_CountryNameAndState_StateNameAndDate(
                countryName, stateName, date).orElseThrow(
                () -> new IllegalArgumentException(
                        "StateCasesPerDate dated " + date.toString() + "in state named "
                                + stateName + " in country named " + countryName + " was not found"));
        return stateCasesPerDateEntity2CasesPerDateConverter.convert(stateCasesPerDateEntity);
    }

    public CoronavirusTrackerCoreProtos.CasesPerDateCollection findAllByCountryNameAndStateNameAndDateRange(String countryName,
                                                                                                            String stateName,
                                                                                                            LocalDate startDate,
                                                                                                            LocalDate endDate) {
        List<StateCasesPerDate> stateCasesPerDateEntities = stateCasesPerDateRepository.findAllByState_Country_CountryNameAndState_StateNameAndDateBetween(
                countryName, stateName, startDate, endDate);
        return stateCasesPerDateEntities2CasesPerDateCollectionConverter.convert(stateCasesPerDateEntities);
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
