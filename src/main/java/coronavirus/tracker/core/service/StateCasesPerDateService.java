package coronavirus.tracker.core.service;

import coronavirus.tracker.core.dto.CasesPerDateData;
import coronavirus.tracker.core.repository.StateCasesPerDateRepository;
import coronavirus.tracker.core.repository.StateRepository;
import coronavirus.tracker.entitycommon.entity.State;
import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateCollectionDTO;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.StateCasesPerDateEntities2CasesPerDateCollectionConverter;
import static coronavirus.tracker.core.utils.ConverterQualifiers.StateCasesPerDateEntity2CasesPerDateConverter;

@Transactional
@Service
@RequiredArgsConstructor
public class StateCasesPerDateService {

    private final StateCasesPerDateRepository stateCasesPerDateRepository;
    private final StateRepository stateRepository;

    @Qualifier(StateCasesPerDateEntity2CasesPerDateConverter)
    private final ConversionService stateCasesPerDateEntity2CasesPerDateConverter;
    @Qualifier(StateCasesPerDateEntities2CasesPerDateCollectionConverter)
    private final ConversionService stateCasesPerDateEntities2CasesPerDateCollectionConverter;


    public CasesPerDateCollectionDTO findAllByCountryNameAndStateName(String countryName, String stateName) {
        List<StateCasesPerDate> stateCasesPerDateCollection =
                stateCasesPerDateRepository
                        .findAllByState_Country_CountryNameAndState_StateName(countryName, stateName);
        return stateCasesPerDateEntities2CasesPerDateCollectionConverter
                .convert(stateCasesPerDateCollection, CasesPerDateCollectionDTO.class);
    }

    public CasesPerDateDTO findOneByCountryNameAndStateNameAndDate(String countryName,
                                                                   String stateName,
                                                                   LocalDate date) {
        StateCasesPerDate stateCasesPerDateEntity = stateCasesPerDateRepository
                .findOneByState_Country_CountryNameAndState_StateNameAndDate(countryName, stateName, date)
                .orElseThrow(() -> new IllegalArgumentException(
                        "StateCasesPerDate dated " + date.toString() + "in state named "
                                + stateName + " in country named " + countryName + " was not found"));
        return stateCasesPerDateEntity2CasesPerDateConverter.convert(stateCasesPerDateEntity, CasesPerDateDTO.class);
    }

    public CasesPerDateCollectionDTO findAllByCountryNameAndStateNameAndDateRange(String countryName,
                                                                                  String stateName,
                                                                                  LocalDate startDate,
                                                                                  LocalDate endDate) {
        List<StateCasesPerDate> stateCasesPerDateEntities =
                stateCasesPerDateRepository.findAllByState_Country_CountryNameAndState_StateNameAndDateBetween(
                        countryName, stateName, startDate, endDate);
        return stateCasesPerDateEntities2CasesPerDateCollectionConverter
                .convert(stateCasesPerDateEntities, CasesPerDateCollectionDTO.class);
    }

    public StateCasesPerDate createStateCasesPerDate(String countryName,
                                                     String stateName,
                                                     CasesPerDateData casesPerDateData) {
        StateCasesPerDate stateCasesPerDate = constructStatePerDateEntity(countryName, stateName, casesPerDateData);
        return stateCasesPerDateRepository.save(stateCasesPerDate);
    }

    public StateCasesPerDate updateStateCasesPerDate(String countryName,
                                                     String stateName,
                                                     LocalDate date,
                                                     CasesPerDateData casesPerDateData) {
        casesPerDateData.setDate(date);
        StateCasesPerDate stateCasesPerDate;
        Optional<StateCasesPerDate> stateCasesPerDateFromRepoOpt =
                stateCasesPerDateRepository.findOneByState_Country_CountryNameAndState_StateNameAndDate(
                        countryName, stateName, date);
        if (stateCasesPerDateFromRepoOpt.isPresent()) {
            stateCasesPerDate = stateCasesPerDateFromRepoOpt.get();
            stateCasesPerDate.setNumberOfCases(casesPerDateData.getNumberOfCases());
        } else {
            stateCasesPerDate = constructStatePerDateEntity(countryName, stateName, casesPerDateData);
        }
        return stateCasesPerDateRepository.save(stateCasesPerDate);
    }

    public void deleteStateCasesPerDate(String countryName, String stateName, LocalDate date) {
        stateCasesPerDateRepository
                .deleteByState_Country_CountryNameAndState_StateNameAndDate(countryName, stateName, date);
    }

    private StateCasesPerDate constructStatePerDateEntity(String countryName,
                                                          String stateName,
                                                          CasesPerDateData casesPerDateData) {
        State stateFromRepo = fetchState(countryName, stateName);
        return new StateCasesPerDate(stateFromRepo, casesPerDateData.getDate(), casesPerDateData.getNumberOfCases());
    }

    private State fetchState(String countryName, String stateName) {
        return stateRepository.findOneByCountry_CountryNameAndStateName(countryName, stateName)
                .orElseThrow(() -> new IllegalArgumentException("State " + stateName + " doesn't exist"));
    }
}
