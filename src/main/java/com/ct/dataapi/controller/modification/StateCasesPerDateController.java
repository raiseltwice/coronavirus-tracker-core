package com.ct.dataapi.controller.modification;

import com.ct.dataapi.dto.CasesPerDateDTO;
import com.ct.entitycommon.entity.StateCasesPerDate;
import com.ct.dataapi.service.StateCasesPerDateService;
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
public class StateCasesPerDateController {

    private final StateCasesPerDateService stateCasesPerDateService;

    @PostMapping("/countries/{countryName}/states/{stateName}/cases-per-date")
    public StateCasesPerDate createStateCasesPerDate(@PathVariable String countryName,
                                                     @PathVariable String stateName,
                                                     @RequestBody CasesPerDateDTO casesPerDateDTO) {
        return stateCasesPerDateService.createStateCasesPerDate(countryName, stateName, casesPerDateDTO);
    }

    @PutMapping("/countries/{countryName}/states/{stateName}/cases-per-date/{date}")
    public StateCasesPerDate updateStateCasesPerDate(
            @PathVariable String countryName,
            @PathVariable String stateName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestBody CasesPerDateDTO casesPerDateDTO) {
        return stateCasesPerDateService.updateStateCasesPerDate(countryName, stateName, date, casesPerDateDTO);
    }

    @DeleteMapping("/countries/{countryName}/states/{stateName}/cases-per-date/{date}")
    public void deleteStateCasesPerDate(
            @PathVariable String countryName,
            @PathVariable String stateName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        stateCasesPerDateService.deleteStateCasesPerDate(countryName, stateName, date);
    }
}
