package coronavirus.tracker.core.service;

import coronavirus.tracker.core.dto.CasesPerDateData;
import coronavirus.tracker.core.repository.CountryCasesPerDateRepository;
import coronavirus.tracker.core.repository.CountryRepository;
import coronavirus.tracker.entitycommon.entity.Country;
import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
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
import static coronavirus.tracker.core.utils.ConverterQualifiers.CountryCasesPerDateEntities2CasesPerDateCollectionConverter;
import static coronavirus.tracker.core.utils.ConverterQualifiers.CountryCasesPerDateEntity2CasesPerDateConverter;

@Transactional
@Service
@RequiredArgsConstructor
public class CountryCasesPerDateService {

    private final CountryCasesPerDateRepository countryCasesPerDateRepository;
    private final CountryRepository countryRepository;

    @Qualifier(CountryCasesPerDateEntity2CasesPerDateConverter)
    private final ConversionService countryCasesPerDateEntity2CasesPerDateConverter;
    @Qualifier(CountryCasesPerDateEntities2CasesPerDateCollectionConverter)
    private final ConversionService countryCasesPerDateEntities2CasesPerDateCollectionConverter;

    public CasesPerDateCollectionDTO findAllByCountryName(String countryName) {
        List<CountryCasesPerDate> countryCasesPerDateEntities =
                countryCasesPerDateRepository.findAllByCountry_CountryName(countryName);
        return countryCasesPerDateEntities2CasesPerDateCollectionConverter
                .convert(countryCasesPerDateEntities, CasesPerDateCollectionDTO.class);
    }

    public CasesPerDateDTO findOneByCountryNameAndDate(String countryName, LocalDate date) {
        CountryCasesPerDate countryCasesPerDate =
                countryCasesPerDateRepository.findOneByCountry_CountryNameAndDate(countryName, date)
                        .orElseThrow(() -> new IllegalArgumentException("CountryCasesPerDate dated " +
                                date.toString() + " in country named " + countryName + " was not found"));
        return countryCasesPerDateEntity2CasesPerDateConverter.convert(countryCasesPerDate, CasesPerDateDTO.class);
    }

    public CasesPerDateCollectionDTO findAllByCountryNameAndDateRange(String countryName,
                                                                      LocalDate startDate,
                                                                      LocalDate endDate) {
        List<CountryCasesPerDate> countryCasesPerDateEntities =
                countryCasesPerDateRepository.findAllByCountry_CountryNameAndDateBetween(countryName, startDate, endDate);
        return countryCasesPerDateEntities2CasesPerDateCollectionConverter
                .convert(countryCasesPerDateEntities, CasesPerDateCollectionDTO.class);
    }

    public CountryCasesPerDate createCountryCasesPerDate(String countryName,
                                                         CasesPerDateData casesPerDateData) {
        CountryCasesPerDate countryCasesPerDate = constructCasesPerDateEntity(countryName, casesPerDateData);
        return countryCasesPerDateRepository.save(countryCasesPerDate);
    }

    public CountryCasesPerDate updateCountryCasesPerDate(String countryName,
                                                         LocalDate date,
                                                         CasesPerDateData casesPerDateData) {
        casesPerDateData.setDate(date);
        CountryCasesPerDate countryCasesPerDate;
        Optional<CountryCasesPerDate> countryCasesPerDateFromRepoOpt =
                countryCasesPerDateRepository.findOneByCountry_CountryNameAndDate(countryName,
                        casesPerDateData.getDate());
        if (countryCasesPerDateFromRepoOpt.isPresent()) {
            countryCasesPerDate = countryCasesPerDateFromRepoOpt.get();
            countryCasesPerDate.setNumberOfCases(casesPerDateData.getNumberOfCases());
        } else {
            countryCasesPerDate = constructCasesPerDateEntity(countryName, casesPerDateData);
        }
        return countryCasesPerDateRepository.save(countryCasesPerDate);
    }

    public void deleteCountryCasesPerDate(String countryName, LocalDate date) {
        countryCasesPerDateRepository.deleteByCountry_CountryNameAndDate(countryName, date);
    }

    private CountryCasesPerDate constructCasesPerDateEntity(String countryName,
                                                            CasesPerDateData casesPerDateData) {
        Country countryFromRepo = fetchCountry(countryName);
        return new CountryCasesPerDate(
                countryFromRepo, casesPerDateData.getDate(), casesPerDateData.getNumberOfCases());
    }

    private Country fetchCountry(String countryName) {
        Optional<Country> countryFromRepoOpt = countryRepository.findOneByCountryName(countryName);
        return countryFromRepoOpt.orElseThrow(() ->
                new IllegalArgumentException("Country " + countryName + " was not found"));
    }
}
