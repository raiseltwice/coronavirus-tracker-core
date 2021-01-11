package coronavirus.tracker.core.converter;

import coronavirus.tracker.entitycommon.entity.Country;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CountryDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.CountryEntity2CountryConverter;

@Component(CountryEntity2CountryConverter)
@NoArgsConstructor
public class CountryEntity2CountryConverter implements Converter<Country, CountryDTO> {

    @Override
    public CountryDTO convert(Country countryEntity) {
        CountryDTO.Builder countryProtoBuilder = CountryDTO.newBuilder()
                .setCountryName(countryEntity.getCountryName());

        if (countryEntity.getLatitude() != null) {
            countryProtoBuilder.setLatitude(countryEntity.getLatitude());
            countryProtoBuilder.setLongitude(countryEntity.getLongitude());
        } else {
            countryProtoBuilder.setLatitudeIsNull(true);
            countryProtoBuilder.setLongitudeIsNull(true);
        }
        return countryProtoBuilder.build();
    }
}
