package com.ct.dataapi.controller.access;

import com.ct.dataapi.protobuf.Protos;
import com.ct.dataapi.service.protobuf.CountryCasesPerDateProtobufService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/x-protobuf")
public class CountryCasesPerDateAccessController {

    private final CountryCasesPerDateProtobufService countryCasesPerDateProtobufService;


    @GetMapping("/countries/{countryName}/cases-per-date")
    public Protos.CasesPerDateCollection findCountryCasesPerDateByCountryName(@PathVariable String countryName) {
        return countryCasesPerDateProtobufService.findAllByCountryName(countryName);
    }

    @GetMapping("/countries/{countryName}/cases-per-date/{date}")
    public Protos.CasesPerDate findCountryCasesPerDateByCountryNameByDate(
            @PathVariable String countryName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return countryCasesPerDateProtobufService.findOneByCountryNameAndDate(countryName, date);
    }

    @GetMapping(value = "/countries/{countryName}/cases-per-date", params = {"startDate", "endDate"})
    public Protos.CasesPerDateCollection findCountryCasesPerDateByCountryNameAndDateRange(
            @PathVariable String countryName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return countryCasesPerDateProtobufService.findAllByCountryNameAndDateRange(countryName, startDate, endDate);
    }

}
