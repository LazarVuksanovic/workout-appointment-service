package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentDto {

    private Long id;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Integer maxPeople;
    private Integer availablePlaces;
    private Long gymId;
    private Long trainingTypeId;
    private Long gymTrainingTypeId;
    private String gymName;
    private String trainingTypeName;
    private BigDecimal price;

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "id=" + id +
                ", date=" + date +
                ", start=" + start +
                ", end=" + end +
                ", maxPeople=" + maxPeople +
                ", availablePlaces=" + availablePlaces +
                ", gymId=" + gymId +
                ", trainingTypeId=" + trainingTypeId +
                ", gymTrainingTypeId=" + gymTrainingTypeId +
                ", gymName='" + gymName + '\'' +
                ", trainingTypeName='" + trainingTypeName + '\'' +
                ", price=" + price +
                '}';
    }
}