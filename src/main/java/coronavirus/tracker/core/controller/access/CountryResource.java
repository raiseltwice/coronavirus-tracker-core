package coronavirus.tracker.core.controller.access;

import coronavirus.tracker.core.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CountriesDTO;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CountryDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/x-protobuf")
public class CountryResource {

    private final CountryService countryService;

    @GetMapping("/countries")
    public CountriesDTO findAllCountries() {
        return countryService.findAllCountries();
    }

    @GetMapping("/countries/{countryName}")
    public CountryDTO findCountryByName(@PathVariable String countryName) {
        return countryService.findOneCountryByName(countryName);
    }
}
