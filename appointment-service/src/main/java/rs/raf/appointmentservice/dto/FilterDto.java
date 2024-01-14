package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
public class FilterDto {

    private String trainingTypes;
    private Integer isIndividual;
    private DayOfWeek dayOfWeek;

    @Override
    public String toString() {
        return "FilterDto{" +
                "TrainingTypes='" + trainingTypes + '\'' +
                ", isIndividual=" + isIndividual +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }
}
