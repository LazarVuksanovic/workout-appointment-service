package rs.raf.appointmentservice.service;

import rs.raf.appointmentservice.client.userservice.dto.IdDto;
import rs.raf.appointmentservice.dto.ScheduledAppointmentDto;

public interface ScheduledAppointmentService {

    ScheduledAppointmentDto scheduleAppointment(String authorization, IdDto appointmentId);
    ScheduledAppointmentDto cancelAppointment(String authorization, IdDto appointmentId);
}
