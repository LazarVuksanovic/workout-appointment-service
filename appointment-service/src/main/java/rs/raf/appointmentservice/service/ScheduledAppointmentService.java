package rs.raf.appointmentservice.service;

import rs.raf.appointmentservice.client.userservice.dto.RoleDto;
import rs.raf.appointmentservice.dto.ScheduledAppointmentDto;

public interface ScheduledAppointmentService {

    ScheduledAppointmentDto scheduleAppointment(String authorization, RoleDto appointmentId);
    ScheduledAppointmentDto cancelAppointment(String authorization, RoleDto appointmentId);
}
