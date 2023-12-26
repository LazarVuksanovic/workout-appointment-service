package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;
import rs.raf.appointmentservice.domain.GymTrainingType;

import java.util.Set;

@Getter
@Setter
public class GymDto {

    private Long id;
    private String name;
    private String description;
    private Integer numOfPersonalCoaches;
}
