package rs.raf.appointmentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.appointmentservice.dto.AppointmentDto;
import rs.raf.appointmentservice.dto.FilterDto;


public interface AppointmentService {

    Page<AppointmentDto> findAll(Pageable pageable, FilterDto filterDto);
    AppointmentDto add(String authorization, AppointmentDto appointmentDto);
    Page<AppointmentDto> findAllGymAppointments(Pageable pageable, String authorization, Long id);
}
