package coronavirus.tracker.core.converter;

import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateCollectionDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.CountryCasesPerDateEntities2CasesPerDateCollectionConverter;

@Component(CountryCasesPerDateEntities2CasesPerDateCollectionConverter)
@RequiredArgsConstructor
public class CountryCasesPerDateEntities2CasesPerDateCollectionConverter
        implements Converter<List<CountryCasesPerDate>, CasesPerDateCollectionDTO> {

    private final CountryCasesPerDateEntity2CasesPerDateConverter countryCasesPerDateEntity2CasesPerDateConverter;

    @Override
    public CasesPerDateCollectionDTO convert(List<CountryCasesPerDate> casesPerDateEntities) {
        return CasesPerDateCollectionDTO.newBuilder()
                .addAllCasesPerDateCollection(
                        casesPerDateEntities.stream()
                                .map(countryCasesPerDateEntity2CasesPerDateConverter::convert)
                                .collect(Collectors.toList())).build();
    }
}
