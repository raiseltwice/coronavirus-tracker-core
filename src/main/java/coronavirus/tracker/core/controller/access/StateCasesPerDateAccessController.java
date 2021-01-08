package coronavirus.tracker.core.controller.access;

import coronavirus.tracker.core.service.StateCasesPerDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDate;
import static coronavirus.tracker.core.api.CoronavirusTrackerCoreProtos.CasesPerDateCollection;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/x-protobuf")
public class StateCasesPerDateAccessController {

    private final StateCasesPerDateService stateCasesPerDateService;

    @GetMapping("/countries/{countryName}/states/{stateName}/cases-per-date")
    public CasesPerDateCollection getStateCasesPerDateByStateName(@PathVariable String countryName,
                                                                         @PathVariable String stateName) {
        return stateCasesPerDateService.findAllByCountryNameAndStateName(countryName, stateName);
    }

    @GetMapping(value = "/countries/{countryName}/states/{stateName}/cases-per-date", params = {"startDate", "endDate"})
    public CasesPerDateCollection getStateCasesPerDateByStateName(
            @PathVariable String countryName,
            @PathVariable String stateName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return stateCasesPerDateService.findAllByCountryNameAndStateNameAndDateRange(
                countryName, stateName, startDate, endDate);
    }

    @GetMapping("/countries/{countryName}/states/{stateName}/cases-per-date/{date}")
    public CasesPerDate getStateCasesPerDateByStateName(
            @PathVariable String countryName,
            @PathVariable String stateName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return stateCasesPerDateService.findOneByCountryNameAndStateNameAndDate(countryName, stateName, date);
    }
}
