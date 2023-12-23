package rs.raf.appointmentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.appointmentservice.dto.AppointmentDto;


public interface AppointmentService {

    Page<AppointmentDto> findAll(Pageable pageable);
}
