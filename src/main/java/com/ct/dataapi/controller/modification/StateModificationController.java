package com.ct.dataapi.controller.modification;

import com.ct.dataapi.dto.StateDTO;
import com.ct.dataapi.entitiestodelete.State;
import com.ct.dataapi.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StateModificationController {

    private final StateService stateService;

    @PostMapping("/countries/{countryName}/states")
    public State createState(@PathVariable String countryName, @RequestBody StateDTO stateDTO) {
        return stateService.createState(countryName, stateDTO);
    }

    @PutMapping("/countries/{countryName}/states/{stateName}")
    public State updateState(@PathVariable String countryName,
                             @PathVariable String stateName,
                             @RequestBody StateDTO stateDTO) {
        return stateService.updateState(countryName, stateName, stateDTO);
    }

    @DeleteMapping("/countries/{countryName}/states/{stateName}")
    public void deleteState(@PathVariable String countryName, @PathVariable String stateName) {
        stateService.deleteState(countryName, stateName);
    }
}
