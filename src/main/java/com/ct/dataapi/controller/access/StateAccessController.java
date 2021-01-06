package com.ct.dataapi.controller.access;

import com.ct.dataapi.protobuf.Protos;
import com.ct.dataapi.service.protobuf.StateProtobufService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/x-protobuf")
public class StateAccessController {

    private final StateProtobufService stateProtobufService;


    @GetMapping("/countries/{countryName}/states")
    public Protos.States findStatesByCountryName(@PathVariable String countryName) {
        return stateProtobufService.findStatesByCountryName(countryName);
    }

    @GetMapping("/countries/{countryName}/states/{stateName}")
    public Protos.State findStateByName(@PathVariable String countryName,
                                        @PathVariable String stateName) {
        return stateProtobufService.findOneByCountryNameAndStateName(countryName, stateName);
    }
}
