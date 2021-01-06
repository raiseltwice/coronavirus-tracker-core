package com.ct.dataapi.mapper;

import com.ct.dataapi.entitiestodelete.Country;
import com.ct.dataapi.entitiestodelete.CountryCasesPerDate;
import com.ct.dataapi.entitiestodelete.State;
import com.ct.dataapi.entitiestodelete.StateCasesPerDate;
import com.ct.dataapi.protobuf.Protos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityToProtobufMapper {

    public Protos.Countries mapCountries(List<Country> countryEntities) {
        List<Protos.Country> countryProtos =
                countryEntities.stream().map(this::mapCountry).collect(Collectors.toList());
        return Protos.Countries.newBuilder()
                .addAllCountries(countryProtos)
                .build();
    }

    public Protos.States mapStates(List<State> stateEntities) {
        List<Protos.State> stateProtos =
                stateEntities.stream().map(this::mapState).collect(Collectors.toList());
        return Protos.States.newBuilder()
                .addAllStates(stateProtos)
                .build();
    }

    public Protos.CasesPerDateCollection mapCountryCasesPerDateCollection(List<CountryCasesPerDate> casesPerDates) {
        return Protos.CasesPerDateCollection.newBuilder()
                .addAllCasesPerDateCollection(
                        casesPerDates.stream().map(this::mapCasesPerDate).collect(Collectors.toList())
                ).build();
    }

    public Protos.CasesPerDateCollection mapStateCasesPerDateCollection(List<StateCasesPerDate> casesPerDates) {
        return Protos.CasesPerDateCollection.newBuilder()
                .addAllCasesPerDateCollection(
                        casesPerDates.stream().map(this::mapCasesPerDate).collect(Collectors.toList())
                ).build();
    }

    public Protos.Country mapCountry(Country countryEntity) {
        Protos.Country.Builder countryProtoBuilder = Protos.Country.newBuilder()
                .setCountryName(countryEntity.getCountryName());

        if (countryEntity.getLatitude() != null) {
            countryProtoBuilder.setLatitude(countryEntity.getLatitude());
            countryProtoBuilder.setLongitude(countryEntity.getLongitude());
        } else {
            countryProtoBuilder.setLatitudeIsNull(true);
            countryProtoBuilder.setLongitudeIsNull(true);
        }
        return countryProtoBuilder.build();
    }

    public Protos.State mapState(State stateEntity) {
        Protos.State.Builder countryProtoBuilder = Protos.State.newBuilder()
                .setStateName(stateEntity.getStateName());

        if (stateEntity.getLatitude() != null) {
            countryProtoBuilder.setLatitude(stateEntity.getLatitude());
            countryProtoBuilder.setLongitude(stateEntity.getLongitude());
        } else {
            countryProtoBuilder.setLatitudeIsNull(true);
            countryProtoBuilder.setLongitudeIsNull(true);
        }
        return countryProtoBuilder.build();
    }

    public Protos.CasesPerDate mapCasesPerDate(CountryCasesPerDate casesPerDate) {
        return Protos.CasesPerDate.newBuilder()
                .setDate(casesPerDate.getDate().toString())
                .setNumberOfCases(casesPerDate.getNumberOfCases())
                .build();
    }

    public Protos.CasesPerDate mapCasesPerDate(StateCasesPerDate casesPerDate) {
        return Protos.CasesPerDate.newBuilder()
                .setDate(casesPerDate.getDate().toString())
                .setNumberOfCases(casesPerDate.getNumberOfCases())
                .build();
    }
}
