package com.ct.dataapi.controller.access;

import com.ct.dataapi.protobuf.Protos;
import com.ct.dataapi.service.protobuf.StateCasesPerDateProtobufService;
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
public class StateCasesPerDateAccessController {

    private final StateCasesPerDateProtobufService stateCasesPerDateProtobufService;

    @GetMapping("/countries/{countryName}/states/{stateName}/cases-per-date")
    public Protos.CasesPerDateCollection getStateCasesPerDateByStateName(@PathVariable String countryName,
                                                                         @PathVariable String stateName) {
        return stateCasesPerDateProtobufService.findAllByCountryNameAndStateName(countryName, stateName);
    }

    @GetMapping(value = "/countries/{countryName}/states/{stateName}/cases-per-date", params = {"startDate", "endDate"})
    public Protos.CasesPerDateCollection getStateCasesPerDateByStateName(
            @PathVariable String countryName,
            @PathVariable String stateName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return stateCasesPerDateProtobufService.findAllByCountryNameAndStateNameAndDateRange(
                countryName, stateName, startDate, endDate);
    }

    @GetMapping("/countries/{countryName}/states/{stateName}/cases-per-date/{date}")
    public Protos.CasesPerDate getStateCasesPerDateByStateName(
            @PathVariable String countryName,
            @PathVariable String stateName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return stateCasesPerDateProtobufService.findOneByCountryNameAndStateNameAndDate(countryName, stateName, date);
    }
}
