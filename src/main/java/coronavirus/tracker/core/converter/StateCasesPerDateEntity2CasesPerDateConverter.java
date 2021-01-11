package coronavirus.tracker.core.converter;

import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.StateCasesPerDateEntity2CasesPerDateConverter;

@Component(StateCasesPerDateEntity2CasesPerDateConverter)
public class StateCasesPerDateEntity2CasesPerDateConverter implements Converter<StateCasesPerDate, CasesPerDateDTO> {

    @Override
    public CasesPerDateDTO convert(StateCasesPerDate casesPerDate) {
        return CasesPerDateDTO.newBuilder()
                .setDate(casesPerDate.getDate().toString())
                .setNumberOfCases(casesPerDate.getNumberOfCases())
                .build();
    }
}
