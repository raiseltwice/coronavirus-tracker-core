package com.ct.dataapi.dto;

import lombok.Data;

import java.time.LocalDate;

// both CountryCasesPerDate and StateCasesPerDate as those hold the same useful(not links) data
@Data
public class CasesPerDateDTO {

    private LocalDate date;
    private Integer numberOfCases;
}
