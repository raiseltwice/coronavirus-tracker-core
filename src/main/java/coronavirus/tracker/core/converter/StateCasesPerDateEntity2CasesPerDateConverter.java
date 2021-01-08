package coronavirus.tracker.core.converter;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StateCasesPerDateEntity2CasesPerDateConverter implements Converter<StateCasesPerDate, CoronavirusTrackerCoreProtos.CasesPerDate> {

    @Override
    public CoronavirusTrackerCoreProtos.CasesPerDate convert(StateCasesPerDate casesPerDate) {
        return CoronavirusTrackerCoreProtos.CasesPerDate.newBuilder()
                .setDate(casesPerDate.getDate().toString())
                .setNumberOfCases(casesPerDate.getNumberOfCases())
                .build();
    }
}
