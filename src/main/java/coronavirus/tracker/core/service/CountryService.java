package coronavirus.tracker.core.service;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.core.converter.CountryEntity2CountryConverter;
import coronavirus.tracker.core.converter.CountyEntities2CountriesConverter;
import coronavirus.tracker.core.dto.CountryDTO;
import coronavirus.tracker.core.mapper.DTOToEntityMapper;
import coronavirus.tracker.core.repository.CountryRepository;
import coronavirus.tracker.entitycommon.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final DTOToEntityMapper dtoToEntityMapper;
    private final CountryEntity2CountryConverter countryEntity2CountryConverter;
    private final CountyEntities2CountriesConverter counrtyEntities2CountriesConverter;

    public CoronavirusTrackerCoreProtos.Countries findAllCountries() {
        List<Country> countryEntities = countryRepository.findAll();
        return counrtyEntities2CountriesConverter.convert(countryEntities);
    }

    public CoronavirusTrackerCoreProtos.Country findOneCountryByName(String countryName) {
        Country countryEntity = countryRepository.findOneByCountryName(countryName).orElseThrow(
                () -> new IllegalArgumentException("Country with name " + countryName + " was not found"));
        return countryEntity2CountryConverter.convert(countryEntity);
    }

    public Country saveCountry(CountryDTO countryDTO) {
        Country countryEntity =
                new Country(countryDTO.getCountryName(), countryDTO.getLatitude(), countryDTO.getLongitude());
        return countryRepository.save(countryEntity);
    }

    public Country updateCountry(String countryName, CountryDTO countryDTO) {
        countryDTO.setCountryName(countryName);
        Country country;
        Optional<Country> countryFromRepoOpt = countryRepository.findOneByCountryName(countryName);
        if (countryFromRepoOpt.isPresent()) {
            country = countryFromRepoOpt.get();
            country.setLatitude(countryDTO.getLatitude());
            country.setLongitude(countryDTO.getLongitude());
        } else {
            country = dtoToEntityMapper.mapCountryDTO(countryDTO);
        }
        return countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(String countryName) {
        countryRepository.deleteByCountryName(countryName);
    }
}
