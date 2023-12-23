package rs.raf.appointmentservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.appointmentservice.dto.ScheduledAppointmentDto;
import rs.raf.appointmentservice.mapper.ScheduledAppointmentMapper;
import rs.raf.appointmentservice.repository.ScheduledAppointmentRepository;
import rs.raf.appointmentservice.service.ScheduledAppointmentService;

@Service
@Transactional
public class ScheduledAppointmentImpl implements ScheduledAppointmentService {

    private ScheduledAppointmentRepository scheduledAppointmentRepository;

    private ScheduledAppointmentMapper scheduledAppointmentMapper;

    public ScheduledAppointmentImpl(ScheduledAppointmentRepository scheduledAppointmentRepository,
                                    ScheduledAppointmentMapper scheduledAppointmentMapper){
        this.scheduledAppointmentRepository = scheduledAppointmentRepository;
        this.scheduledAppointmentMapper = scheduledAppointmentMapper;
    }

    @Override
    public ScheduledAppointmentDto scheduleAppointment(ScheduledAppointmentDto scheduledAppointmentDto) {
        this.scheduledAppointmentRepository.save(this.scheduledAppointmentMapper.scheduledAppointmentDtoToScheduledAppointment(scheduledAppointmentDto));
        return scheduledAppointmentDto;
    }
}
