package coronavirus.tracker.core.converter;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StateCasesPerDateEntities2CasesPerDateCollectionConverter
        implements Converter<List<StateCasesPerDate>, CoronavirusTrackerCoreProtos.CasesPerDateCollection> {

    private final StateCasesPerDateEntity2CasesPerDateConverter stateCasesPerDateEntity2CasesPerDateConverter;

    @Override
    public CoronavirusTrackerCoreProtos.CasesPerDateCollection convert(List<StateCasesPerDate> casesPerDateEntities) {
        return CoronavirusTrackerCoreProtos.CasesPerDateCollection.newBuilder()
                .addAllCasesPerDateCollection(
                        casesPerDateEntities.stream().map(stateCasesPerDateEntity2CasesPerDateConverter::convert)
                                .collect(Collectors.toList())
                ).build();
    }
}
