package coronavirus.tracker.core.controller.access;

import coronavirus.tracker.core.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.StateDTO;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.StatesDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/x-protobuf")
public class StateResource {

    private final StateService stateService;


    @GetMapping("/countries/{countryName}/states")
    public StatesDTO findStatesByCountryName(@PathVariable String countryName) {
        return stateService.findStatesByCountryName(countryName);
    }

    @GetMapping("/countries/{countryName}/states/{stateName}")
    public StateDTO findStateByName(@PathVariable String countryName,
                                        @PathVariable String stateName) {
        return stateService.findOneByCountryNameAndStateName(countryName, stateName);
    }
}
