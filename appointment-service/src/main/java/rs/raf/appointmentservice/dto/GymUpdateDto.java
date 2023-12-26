package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;
import rs.raf.appointmentservice.domain.GymTrainingType;

import java.util.Set;

@Getter
@Setter
public class GymUpdateDto {

    private String name;
    private String description;
    private Integer numOfPersonalCoaches;
}
