package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingTypeDto {

    private Long id;
    private String name;
    private boolean isIndividual;
}
