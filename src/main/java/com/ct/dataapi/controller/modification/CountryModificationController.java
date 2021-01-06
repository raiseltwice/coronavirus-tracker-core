package com.ct.dataapi.controller.modification;

import com.ct.dataapi.dto.CountryDTO;
import com.ct.dataapi.entitiestodelete.Country;
import com.ct.dataapi.service.CountryService;
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
    public Country createCountry(@RequestBody CountryDTO countryDTO) {
        return countryService.saveCountry(countryDTO);
    }

    @PutMapping("/countries/{countryName}")
    public Country updateCountry(@PathVariable String countryName, @RequestBody CountryDTO countryDTO) {
        return countryService.updateCountry(countryName, countryDTO);
    }

    @DeleteMapping("/countries/{countryName}")
    public void deleteCountry(@PathVariable String countryName) {
        countryService.deleteCountry(countryName);
    }
}
