package coronavirus.tracker.core.dto;

import lombok.Data;

import java.time.LocalDate;

// both CountryCasesPerDate and StateCasesPerDate as those hold the same useful(not links) data
@Data
public class CasesPerDateData {

    private LocalDate date;
    private Integer numberOfCases;
}
