package coronavirus.tracker.core.dto;

import lombok.Data;

@Data
public class CountryData {

    private String countryName;
    private Double latitude;
    private Double longitude;
}
