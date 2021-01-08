package coronavirus.tracker.core.converter;

import coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos;
import coronavirus.tracker.entitycommon.entity.State;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StateEntity2StateConverter implements Converter<State, CoronavirusTrackerCoreProtos.State> {

    @Override
    public CoronavirusTrackerCoreProtos.State convert(State stateEntity) {
        CoronavirusTrackerCoreProtos.State.Builder countryProtoBuilder = CoronavirusTrackerCoreProtos.State.newBuilder()
                .setStateName(stateEntity.getStateName());

        if (stateEntity.getLatitude() != null) {
            countryProtoBuilder.setLatitude(stateEntity.getLatitude());
            countryProtoBuilder.setLongitude(stateEntity.getLongitude());
        } else {
            countryProtoBuilder.setLatitudeIsNull(true);
            countryProtoBuilder.setLongitudeIsNull(true);
        }
        return countryProtoBuilder.build();
    }
}
