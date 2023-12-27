package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NewGymTrainingTypeDto {

    private Long id;
    private BigDecimal price;
}
