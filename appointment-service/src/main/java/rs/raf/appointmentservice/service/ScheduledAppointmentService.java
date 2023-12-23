package rs.raf.appointmentservice.service;

import rs.raf.appointmentservice.dto.ScheduledAppointmentDto;

public interface ScheduledAppointmentService {

    ScheduledAppointmentDto scheduleAppointment(ScheduledAppointmentDto scheduledAppointmentDto);
}
