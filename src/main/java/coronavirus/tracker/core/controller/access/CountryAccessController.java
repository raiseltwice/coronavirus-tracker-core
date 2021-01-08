package coronavirus.tracker.core.controller.access;

import coronavirus.tracker.core.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.Countries;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.Country;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/x-protobuf")
public class CountryAccessController {

    private final CountryService countryService;

    @GetMapping("/countries")
    public Countries findAllCountries() {
        return countryService.findAllCountries();
    }

    @GetMapping("/countries/{countryName}")
    public Country findCountryByName(@PathVariable String countryName) {
        return countryService.findOneCountryByName(countryName);
    }
}
