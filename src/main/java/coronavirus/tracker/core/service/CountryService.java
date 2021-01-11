package coronavirus.tracker.core.service;

import coronavirus.tracker.core.dto.CountryData;
import coronavirus.tracker.core.repository.CountryRepository;
import coronavirus.tracker.entitycommon.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CountriesDTO;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CountryDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.CountryEntities2CountriesConverter;
import static coronavirus.tracker.core.utils.ConverterQualifiers.CountryEntity2CountryConverter;

@Transactional
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @Qualifier(CountryEntity2CountryConverter)
    private final ConversionService countryEntity2CountryConverter;
    @Qualifier(CountryEntities2CountriesConverter)
    private final ConversionService countryEntities2CountriesConverter;

    public CountriesDTO findAllCountries() {
        List<Country> countryEntities = countryRepository.findAll();
        return countryEntities2CountriesConverter.convert(countryEntities, CountriesDTO.class);
    }

    public CountryDTO findOneCountryByName(String countryName) {
        Country countryEntity = countryRepository.findOneByCountryName(countryName).orElseThrow(
                () -> new IllegalArgumentException("Country with name " + countryName + " was not found"));
        return countryEntity2CountryConverter.convert(countryEntity, CountryDTO.class);
    }

    public Country saveCountry(CountryData countryData) {
        Country countryEntity =
                new Country(countryData.getCountryName(), countryData.getLatitude(), countryData.getLongitude());
        return countryRepository.save(countryEntity);
    }

    public Country updateCountry(String countryName, CountryData countryData) {
        countryData.setCountryName(countryName);
        Country country;
        Optional<Country> countryFromRepoOpt = countryRepository.findOneByCountryName(countryName);
        if (countryFromRepoOpt.isPresent()) {
            country = countryFromRepoOpt.get();
            country.setLatitude(countryData.getLatitude());
            country.setLongitude(countryData.getLongitude());
        } else {
            country = new Country(countryData.getCountryName(), countryData.getLatitude(), countryData.getLongitude());
        }
        return countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(String countryName) {
        countryRepository.deleteByCountryName(countryName);
    }
}
