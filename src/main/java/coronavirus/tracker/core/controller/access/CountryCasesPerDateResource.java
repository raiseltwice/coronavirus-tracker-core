package coronavirus.tracker.core.controller.access;


import coronavirus.tracker.core.service.CountryCasesPerDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateDTO;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateCollectionDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/x-protobuf")
public class CountryCasesPerDateResource {

    private final CountryCasesPerDateService countryCasesPerDateService;

    @GetMapping("/countries/{countryName}/cases-per-date")
    public CasesPerDateCollectionDTO findCountryCasesPerDateByCountryName(@PathVariable String countryName) {
        return countryCasesPerDateService.findAllByCountryName(countryName);
    }

    @GetMapping("/countries/{countryName}/cases-per-date/{date}")
    public CasesPerDateDTO findCountryCasesPerDateByCountryNameByDate(
            @PathVariable String countryName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return countryCasesPerDateService.findOneByCountryNameAndDate(countryName, date);
    }

    @GetMapping(value = "/countries/{countryName}/cases-per-date", params = {"startDate", "endDate"})
    public CasesPerDateCollectionDTO findCountryCasesPerDateByCountryNameAndDateRange(
            @PathVariable String countryName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return countryCasesPerDateService.findAllByCountryNameAndDateRange(countryName, startDate, endDate);
    }
}
