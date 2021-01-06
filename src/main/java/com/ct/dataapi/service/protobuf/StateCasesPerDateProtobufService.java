package com.ct.dataapi.service.protobuf;

import com.ct.entitycommon.entity.StateCasesPerDate;
import com.ct.dataapi.mapper.EntityToProtobufMapper;
import ct.coreapi.common.Protos;
import com.ct.dataapi.service.StateCasesPerDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StateCasesPerDateProtobufService {

    private final StateCasesPerDateService countryCasesPerDateService;
    private final EntityToProtobufMapper entityToProtobufMapper;

    public Protos.CasesPerDateCollection findAllByCountryNameAndStateName(String countryName, String stateName) {
        List<StateCasesPerDate> stateCasesPerDateCollection =
                countryCasesPerDateService.findAllByCountryNameAndStateName(countryName, stateName);
        return entityToProtobufMapper.mapStateCasesPerDateCollection(stateCasesPerDateCollection);
    }

    public Protos.CasesPerDateCollection findAllByCountryNameAndStateNameAndDateRange(String countryName,
                                                                                      String stateName,
                                                                                      LocalDate startDate,
                                                                                      LocalDate endDate) {
        List<StateCasesPerDate> stateCasesPerDateCollection =
                countryCasesPerDateService.findAllByCountryNameAndStateNameAndDateRange(
                        countryName, stateName, startDate, endDate);

        return entityToProtobufMapper.mapStateCasesPerDateCollection(stateCasesPerDateCollection);
    }

    public Protos.CasesPerDate findOneByCountryNameAndStateNameAndDate(String countryName, String stateName, LocalDate date) {
        StateCasesPerDate stateCasesPerDate =
                countryCasesPerDateService.findOneByCountryNameAndStateNameAndDate(countryName, stateName, date);
        return entityToProtobufMapper.mapCasesPerDate(stateCasesPerDate);
    }
}
