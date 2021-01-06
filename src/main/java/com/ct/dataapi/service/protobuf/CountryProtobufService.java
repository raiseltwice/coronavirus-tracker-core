package com.ct.dataapi.service.protobuf;

import com.ct.entitycommon.entity.Country;
import com.ct.dataapi.mapper.EntityToProtobufMapper;
import ct.coreapi.common.Protos;
import com.ct.dataapi.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryProtobufService {

    private final CountryService countryService;
    private final EntityToProtobufMapper entityToProtobufMapper;

    public Protos.Countries findAllCountries() {
        List<Country> countries = countryService.findAllCountries();
        return entityToProtobufMapper.mapCountries(countries);
    }

    public Protos.Country findOneCountryByName(String countryName) {
        Country country = countryService.findOneCountryByName(countryName);
        return entityToProtobufMapper.mapCountry(country);
    }
}
