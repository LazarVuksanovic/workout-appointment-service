package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
public class FilterDto {

    private List<Long> TrainingTypeId;
    private Integer isIndividual;
    private DayOfWeek dayOfWeek;
}
