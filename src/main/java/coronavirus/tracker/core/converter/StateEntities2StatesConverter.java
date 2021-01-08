package coronavirus.tracker.core.converter;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.entitycommon.entity.State;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StateEntities2StatesConverter implements Converter<List<State>, CoronavirusTrackerCoreProtos.States> {

    private final StateEntity2StateConverter stateEntity2StateConverter;

    @Override
    public CoronavirusTrackerCoreProtos.States convert(List<State> stateEntities) {
        List<CoronavirusTrackerCoreProtos.State> stateProtos =
                stateEntities.stream().map(stateEntity2StateConverter::convert).collect(Collectors.toList());
        return CoronavirusTrackerCoreProtos.States.newBuilder()
                .addAllStates(stateProtos)
                .build();
    }
}
