package coronavirus.tracker.core.controller.modification;

import coronavirus.tracker.core.dto.CountryData;
import coronavirus.tracker.entitycommon.entity.Country;
import coronavirus.tracker.core.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CountryModificationController {

    private final CountryService countryService;

    @PostMapping("/countries")
    public Country createCountry(@RequestBody CountryData countryData) {
        return countryService.saveCountry(countryData);
    }

    @PutMapping("/countries/{countryName}")
    public Country updateCountry(@PathVariable String countryName, @RequestBody CountryData countryData) {
        return countryService.updateCountry(countryName, countryData);
    }

    @DeleteMapping("/countries/{countryName}")
    public void deleteCountry(@PathVariable String countryName) {
        countryService.deleteCountry(countryName);
    }
}
