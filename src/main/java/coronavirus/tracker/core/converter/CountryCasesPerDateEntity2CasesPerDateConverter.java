package coronavirus.tracker.core.converter;

import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.CountryCasesPerDateEntity2CasesPerDateConverter;

@Component(CountryCasesPerDateEntity2CasesPerDateConverter)
public class CountryCasesPerDateEntity2CasesPerDateConverter implements Converter<CountryCasesPerDate, CasesPerDateDTO> {

    @Override
    public CasesPerDateDTO convert(CountryCasesPerDate casesPerDate) {
        return CasesPerDateDTO.newBuilder()
                .setDate(casesPerDate.getDate().toString())
                .setNumberOfCases(casesPerDate.getNumberOfCases())
                .build();
    }
}
