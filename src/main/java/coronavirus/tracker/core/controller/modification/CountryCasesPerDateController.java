package coronavirus.tracker.core.controller.modification;

import coronavirus.tracker.core.dto.CasesPerDateData;
import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import coronavirus.tracker.core.service.CountryCasesPerDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CountryCasesPerDateController {

    private final CountryCasesPerDateService countryCasesPerDateService;

    @PostMapping("/countries/{countryName}/cases-per-date")
    public CountryCasesPerDate createCountryCasesPerDate(@PathVariable String countryName,
                                                         @RequestBody CasesPerDateData casesPerDateData) {
        return countryCasesPerDateService.createCountryCasesPerDate(countryName, casesPerDateData);
    }

    @PutMapping("/countries/{countryName}/cases-per-date/{date}")
    public CountryCasesPerDate updateCountryCasesPerDate(
            @PathVariable String countryName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestBody CasesPerDateData casesPerDateData) {
        return countryCasesPerDateService.updateCountryCasesPerDate(countryName, date, casesPerDateData);
    }

    @DeleteMapping("/countries/{countryName}/cases-per-date/{date}")
    public void deleteCountryCasesPerDate(@PathVariable String countryName,
                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        countryCasesPerDateService.deleteCountryCasesPerDate(countryName, date);
    }
}
