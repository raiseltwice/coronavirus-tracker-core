package coronavirus.tracker.core.converter;

import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateCollectionDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.StateCasesPerDateEntities2CasesPerDateCollectionConverter;

@Component(StateCasesPerDateEntities2CasesPerDateCollectionConverter)
@RequiredArgsConstructor
public class StateCasesPerDateEntities2CasesPerDateCollectionConverter
        implements Converter<List<StateCasesPerDate>, CasesPerDateCollectionDTO> {

    private final StateCasesPerDateEntity2CasesPerDateConverter stateCasesPerDateEntity2CasesPerDateConverter;

    @Override
    public CasesPerDateCollectionDTO convert(List<StateCasesPerDate> casesPerDateEntities) {
        return CasesPerDateCollectionDTO.newBuilder()
                .addAllCasesPerDateCollection(
                        casesPerDateEntities.stream()
                                .map(stateCasesPerDateEntity2CasesPerDateConverter::convert)
                                .collect(Collectors.toList())).build();
    }
}
