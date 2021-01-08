package coronavirus.tracker.core.converter;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountryCasesPerDateEntities2CasesPerDateCollectionConverter
        implements Converter<List<CountryCasesPerDate>, CoronavirusTrackerCoreProtos.CasesPerDateCollection> {

    private final CountryCasesPerDateEntity2CasesPerDateConverter countryCasesPerDateEntity2CasesPerDateConverter;

    @Override
    public CoronavirusTrackerCoreProtos.CasesPerDateCollection convert(List<CountryCasesPerDate> casesPerDateEntities) {
        return CoronavirusTrackerCoreProtos.CasesPerDateCollection.newBuilder()
                .addAllCasesPerDateCollection(
                        casesPerDateEntities.stream().map(countryCasesPerDateEntity2CasesPerDateConverter::convert)
                                .collect(Collectors.toList())
                ).build();
    }
}
