package coronavirus.tracker.core.converter;

import coronavirus.tracker.entitycommon.entity.State;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.StateDTO;
import static coronavirus.tracker.core.utils.ConverterQualifiers.StateEntity2StateConverter;

@Component(StateEntity2StateConverter)
public class StateEntity2StateConverter implements Converter<State, StateDTO> {

    @Override
    public StateDTO convert(State stateEntity) {
        StateDTO.Builder countryProtoBuilder = StateDTO.newBuilder()
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
