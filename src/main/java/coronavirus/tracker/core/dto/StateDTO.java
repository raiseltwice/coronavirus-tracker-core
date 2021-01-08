package coronavirus.tracker.core.dto;

import lombok.Data;

@Data
public class StateDTO {

    private String stateName;
    private Double latitude;
    private Double longitude;
}
