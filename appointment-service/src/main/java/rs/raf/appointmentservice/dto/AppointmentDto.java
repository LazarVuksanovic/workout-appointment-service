package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

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
    private String gymName;
    private String trainingTypeName;
}