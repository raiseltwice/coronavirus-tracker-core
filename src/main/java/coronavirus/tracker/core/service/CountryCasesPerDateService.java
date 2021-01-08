package coronavirus.tracker.core.service;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.core.converter.CountryCasesPerDateEntities2CasesPerDateCollectionConverter;
import coronavirus.tracker.core.converter.CountryCasesPerDateEntity2CasesPerDateConverter;
import coronavirus.tracker.core.dto.CasesPerDateDTO;
import coronavirus.tracker.entitycommon.entity.Country;
import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import coronavirus.tracker.core.mapper.DTOToEntityMapper;
import coronavirus.tracker.core.repository.CountryCasesPerDateRepository;
import coronavirus.tracker.core.repository.CountryRepository;
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
    private final CountryCasesPerDateEntity2CasesPerDateConverter countryCasesPerDateEntity2CasesPerDateConverter;
    private final CountryCasesPerDateEntities2CasesPerDateCollectionConverter countryCasesPerDateEntities2CasesPerDateCollectionConverter;

    public CoronavirusTrackerCoreProtos.CasesPerDateCollection findAllByCountryName(String countryName) {
        List<CountryCasesPerDate> countryCasesPerDateEntities =
                countryCasesPerDateRepository.findAllByCountry_CountryName(countryName);
        return countryCasesPerDateEntities2CasesPerDateCollectionConverter.convert(countryCasesPerDateEntities);
    }

    public CoronavirusTrackerCoreProtos.CasesPerDate findOneByCountryNameAndDate(String countryName, LocalDate date) {
        CountryCasesPerDate countryCasesPerDate =
                countryCasesPerDateRepository.findOneByCountry_CountryNameAndDate(countryName, date).orElseThrow(
                        () -> new IllegalArgumentException("CountryCasesPerDate dated " +
                                date.toString() + " in country named " + countryName + " was not found"));
        return countryCasesPerDateEntity2CasesPerDateConverter.convert(countryCasesPerDate);
    }

    public CoronavirusTrackerCoreProtos.CasesPerDateCollection findAllByCountryNameAndDateRange(String countryName,
                                                                                                LocalDate startDate,
                                                                                                LocalDate endDate) {
        List<CountryCasesPerDate> countryCasesPerDateEntities =
                countryCasesPerDateRepository.findAllByCountry_CountryNameAndDateBetween(countryName, startDate, endDate);
        return countryCasesPerDateEntities2CasesPerDateCollectionConverter.convert(countryCasesPerDateEntities);
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
                new IllegalArgumentException("Country " + countryName + " was not found"));
    }
}
