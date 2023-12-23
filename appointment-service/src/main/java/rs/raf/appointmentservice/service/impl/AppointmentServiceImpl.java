package rs.raf.appointmentservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.appointmentservice.dto.AppointmentDto;
import rs.raf.appointmentservice.mapper.AppointmentMapper;
import rs.raf.appointmentservice.repository.AppointmentRepository;
import rs.raf.appointmentservice.service.AppointmentService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;
    private AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper){
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public Page<AppointmentDto> findAll(Pageable pageable) {
        return this.appointmentRepository.findAll(pageable).map(appointmentMapper::appointmentToAppointmentDto);
    }
}
