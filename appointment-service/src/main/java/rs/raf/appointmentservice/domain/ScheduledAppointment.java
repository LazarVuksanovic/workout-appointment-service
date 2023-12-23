package rs.raf.appointmentservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ScheduledAppointment {

    @EmbeddedId
    private ScheduledAppointmentId id = new ScheduledAppointmentId();
}


