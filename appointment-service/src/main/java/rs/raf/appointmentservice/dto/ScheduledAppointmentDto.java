package rs.raf.appointmentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduledAppointmentDto {

    private Long userId;
    private Long appointmentId;

    public ScheduledAppointmentDto(Long userId, Long appointmentId){
        this.userId = userId;
        this.appointmentId = appointmentId;
    }

    public ScheduledAppointmentDto(){

    }

}


