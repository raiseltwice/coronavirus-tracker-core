package coronavirus.tracker.core.converter;

import coronavirus.tracker.entitycommon.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CountriesDTO;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CountryDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.CountryEntities2CountriesConverter;

@Component(CountryEntities2CountriesConverter)
@RequiredArgsConstructor
public class CountryEntities2CountriesConverter implements Converter<List<Country>, CountriesDTO> {

    private final CountryEntity2CountryConverter countryEntity2CountryConverter;

    @Override
    public CountriesDTO convert(List<Country> countryEntities) {
        List<CountryDTO> countryProtos =
                countryEntities.stream()
                        .map(countryEntity2CountryConverter::convert)
                        .collect(Collectors.toList());
        return CountriesDTO.newBuilder()
                .addAllCountries(countryProtos)
                .build();
    }
}
