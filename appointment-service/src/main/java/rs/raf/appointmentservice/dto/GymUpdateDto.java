package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymUpdateDto {

    private String name;
    private String description;
    private Integer numOfPersonalCoaches;
}
