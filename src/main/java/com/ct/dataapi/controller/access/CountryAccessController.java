package com.ct.dataapi.controller.access;

import com.ct.dataapi.protobuf.Protos;
import com.ct.dataapi.service.protobuf.CountryProtobufService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/x-protobuf")
public class CountryAccessController {

    private final CountryProtobufService countryProtobufService;

    @GetMapping("/countries")
    public Protos.Countries findAllCountries() {
        return countryProtobufService.findAllCountries();
    }

    @GetMapping("/countries/{countryName}")
    public Protos.Country findCountryByName(@PathVariable String countryName) {
        return countryProtobufService.findOneCountryByName(countryName);
    }
}
