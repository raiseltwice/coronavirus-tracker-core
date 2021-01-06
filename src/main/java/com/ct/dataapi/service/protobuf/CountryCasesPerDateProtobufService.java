package com.ct.dataapi.service.protobuf;

import com.ct.entitycommon.entity.CountryCasesPerDate;
import com.ct.dataapi.mapper.EntityToProtobufMapper;
import ct.coreapi.common.Protos;
import com.ct.dataapi.service.CountryCasesPerDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryCasesPerDateProtobufService {

    private final CountryCasesPerDateService countryCasesPerDateService;
    private final EntityToProtobufMapper entityToProtobufMapper;


    public Protos.CasesPerDateCollection findAllByCountryName(String countryName) {
        List<CountryCasesPerDate> countryCasesPerDateCollection =
                countryCasesPerDateService.findAllByCountryName(countryName);
        return entityToProtobufMapper.mapCountryCasesPerDateCollection(countryCasesPerDateCollection);
    }

    public Protos.CasesPerDate findOneByCountryNameAndDate(String countryName, LocalDate date) {
        CountryCasesPerDate casesPerDate =
                countryCasesPerDateService.findOneByCountryNameAndDate(countryName, date);
        return entityToProtobufMapper.mapCasesPerDate(casesPerDate);
    }

    public Protos.CasesPerDateCollection findAllByCountryNameAndDateRange(String countryName,
                                                                          LocalDate startDate,
                                                                          LocalDate endDate) {
        List<CountryCasesPerDate> countryCasesPerDateCollection =
                countryCasesPerDateService.findAllByCountryNameAndDateRange(countryName, startDate, endDate);
        return entityToProtobufMapper.mapCountryCasesPerDateCollection(countryCasesPerDateCollection);
    }
}
