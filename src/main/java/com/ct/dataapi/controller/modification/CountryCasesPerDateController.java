package com.ct.dataapi.controller.modification;

import com.ct.dataapi.dto.CasesPerDateDTO;
import com.ct.dataapi.entitiestodelete.CountryCasesPerDate;
import com.ct.dataapi.service.CountryCasesPerDateService;
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
                                                         @RequestBody CasesPerDateDTO casesPerDateDTO) {
        return countryCasesPerDateService.createCountryCasesPerDate(countryName, casesPerDateDTO);
    }

    @PutMapping("/countries/{countryName}/cases-per-date/{date}")
    public CountryCasesPerDate updateCountryCasesPerDate(
            @PathVariable String countryName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestBody CasesPerDateDTO casesPerDateDTO) {
        return countryCasesPerDateService.updateCountryCasesPerDate(countryName, date, casesPerDateDTO);
    }

    @DeleteMapping("/countries/{countryName}/cases-per-date/{date}")
    public void deleteCountryCasesPerDate(@PathVariable String countryName,
                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        countryCasesPerDateService.deleteCountryCasesPerDate(countryName, date);
    }
}
