package coronavirus.tracker.core.converter;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.entitycommon.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountyEntities2CountriesConverter implements Converter<List<Country>, CoronavirusTrackerCoreProtos.Countries> {

    private final CountryEntity2CountryConverter countryEntity2CountryConverter;

    @Override
    public CoronavirusTrackerCoreProtos.Countries convert(List<Country> countryEntities) {
        List<CoronavirusTrackerCoreProtos.Country> countryProtos =
                countryEntities.stream().map(countryEntity2CountryConverter::convert).collect(Collectors.toList());
        return CoronavirusTrackerCoreProtos.Countries.newBuilder()
                .addAllCountries(countryProtos)
                .build();
    }
}
