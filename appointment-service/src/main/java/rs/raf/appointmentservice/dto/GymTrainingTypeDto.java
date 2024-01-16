package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GymTrainingTypeDto {
    private Long id;
    private Long gymId;
    private Long trainingTypeId;
    private BigDecimal price;
    private String trainingTypeName;
}
