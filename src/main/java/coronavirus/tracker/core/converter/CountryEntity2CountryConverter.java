package coronavirus.tracker.core.converter;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.entitycommon.entity.Country;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CountryEntity2CountryConverter implements Converter<Country, CoronavirusTrackerCoreProtos.Country> {

    @Override
    public CoronavirusTrackerCoreProtos.Country convert(Country countryEntity) {
        CoronavirusTrackerCoreProtos.Country.Builder countryProtoBuilder = CoronavirusTrackerCoreProtos.Country.newBuilder()
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
