package com.ct.dataapi.service.protobuf;

import com.ct.entitycommon.entity.State;
import com.ct.dataapi.mapper.EntityToProtobufMapper;
import ct.coreapi.common.Protos;
import com.ct.dataapi.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateProtobufService {

    private final StateService stateService;
    private final EntityToProtobufMapper entityToProtobufMapper;

    public Protos.States findStatesByCountryName(String countryName) {
        List<State> states = stateService.findStatesByCountryName(countryName);
        return entityToProtobufMapper.mapStates(states);
    }

    public Protos.State findOneByCountryNameAndStateName(String countryName, String stateName) {
        State state = stateService.findOneByCountryNameAndStateName(countryName, stateName);
        return entityToProtobufMapper.mapState(state);
    }
}
