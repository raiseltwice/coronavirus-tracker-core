package coronavirus.tracker.core.dto;

import lombok.Data;

@Data
public class CountryDTO {

    private String countryName;
    private Double latitude;
    private Double longitude;
}
