package rs.raf.appointmentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.appointmentservice.client.userservice.dto.IdDto;
import rs.raf.appointmentservice.dto.AppointmentDto;
import rs.raf.appointmentservice.dto.FilterDto;
import rs.raf.appointmentservice.dto.ScheduledAppointmentDto;

public interface ScheduledAppointmentService {

    Page<AppointmentDto> findUserAppointments(Pageable pageable, String authorization, FilterDto filterDto);
    ScheduledAppointmentDto scheduleAppointment(String authorization, IdDto appointmentId);
    ScheduledAppointmentDto cancelAppointment(String authorization, IdDto appointmentId);
}
