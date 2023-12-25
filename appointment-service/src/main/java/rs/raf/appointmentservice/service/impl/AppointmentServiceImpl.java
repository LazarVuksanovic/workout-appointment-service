package rs.raf.appointmentservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.dto.AppointmentDto;
import rs.raf.appointmentservice.dto.FilterDto;
import rs.raf.appointmentservice.mapper.AppointmentMapper;
import rs.raf.appointmentservice.repository.AppointmentRepository;
import rs.raf.appointmentservice.service.AppointmentService;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Page<AppointmentDto> findAll(Pageable pageable, FilterDto filterDto) {
        Page<Appointment> appointments = this.appointmentRepository.findAll(pageable);
        List<Appointment> appointmentsList =  appointments.stream()
                .filter(appointment -> filterByTrainingType(appointment, filterDto.getTrainingTypeId()))
                .filter(appointment -> filterByIsIndividual(appointment, filterDto.getIsIndividual()))
                .filter(appointment -> filterByDayOfWeek(appointment, filterDto.getDayOfWeek()))
                .filter(appointment -> appointment.getAvailablePlaces() > 0)
                .collect(Collectors.toList());
        appointments = new PageImpl<>(appointmentsList, pageable, appointmentsList.size());
        return appointments.map(appointmentMapper::appointmentToAppointmentDto);
    }

    // Filtriranje po trainingTypeId
    private boolean filterByTrainingType(Appointment appointment, List<Long> trainingTypeId) {
        return trainingTypeId == null || trainingTypeId.contains(appointment.getTrainingType().getId());
    }

    // Filtriranje po isIndividual
    private boolean filterByIsIndividual(Appointment appointment, Integer isIndividual) {
        if (isIndividual == null)
                return true;
        return (isIndividual == 1) == (appointment.getMaxPeople() == 1);
    }

    // Filtriranje po danu u nedelji
    private boolean filterByDayOfWeek(Appointment appointment, DayOfWeek dayOfWeek) {
        return dayOfWeek == null || appointment.getDate().getDayOfWeek().equals(dayOfWeek);
    }
}
