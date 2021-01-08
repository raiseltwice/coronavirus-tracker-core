package coronavirus.tracker.core.converter;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CountryCasesPerDateEntity2CasesPerDateConverter implements Converter<CountryCasesPerDate, CoronavirusTrackerCoreProtos.CasesPerDate> {

    @Override
    public CoronavirusTrackerCoreProtos.CasesPerDate convert(CountryCasesPerDate casesPerDate) {
        return CoronavirusTrackerCoreProtos.CasesPerDate.newBuilder()
                .setDate(casesPerDate.getDate().toString())
                .setNumberOfCases(casesPerDate.getNumberOfCases())
                .build();
    }
}
