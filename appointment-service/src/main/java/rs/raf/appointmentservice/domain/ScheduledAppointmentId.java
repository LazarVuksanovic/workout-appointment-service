package rs.raf.appointmentservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ScheduledAppointmentId implements Serializable {

    private Long userId;
    private Long appointmentId;


    public ScheduledAppointmentId(Long userId, Long appointmentId){
        this.userId = userId;
        this.appointmentId = appointmentId;
    }

    public ScheduledAppointmentId(){

    }
}
