package coronavirus.tracker.core.converter;

import coronavirus.tracker.entitycommon.entity.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.StateDTO;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.StatesDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.StateEntities2StatesConverter;

@Component(StateEntities2StatesConverter)
@RequiredArgsConstructor
public class StateEntities2StatesConverter implements Converter<List<State>, StatesDTO> {

    private final StateEntity2StateConverter stateEntity2StateConverter;

    @Override
    public StatesDTO convert(List<State> stateEntities) {
        List<StateDTO> stateProtos =
                stateEntities.stream().map(stateEntity2StateConverter::convert)
                        .collect(Collectors.toList());
        return StatesDTO.newBuilder()
                .addAllStates(stateProtos)
                .build();
    }
}
