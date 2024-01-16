package rs.raf.appointmentservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.raf.appointmentservice.client.userservice.dto.UserDto;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.dto.AppointmentDto;
import rs.raf.appointmentservice.dto.FilterDto;
import rs.raf.appointmentservice.exception.NotFoundException;
import rs.raf.appointmentservice.mapper.AppointmentMapper;
import rs.raf.appointmentservice.repository.*;
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
    private GymRepository gymRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private GymTrainingTypeRepository gymTrainingTypeRepository;
    private RestTemplate userServiceRestTemplate;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper,
                                  GymRepository gymRepository, TrainingTypeRepository trainingTypeRepository,
                                  ScheduledAppointmentRepository scheduledAppointmentRepository,
                                  GymTrainingTypeRepository gymTrainingTypeRepository, RestTemplate userServiceRestTemplate){
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.gymRepository = gymRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.scheduledAppointmentRepository = scheduledAppointmentRepository;
        this.gymTrainingTypeRepository = gymTrainingTypeRepository;
        this.userServiceRestTemplate = userServiceRestTemplate;
    }

    @Override
    public Page<AppointmentDto> findAll(Pageable pageable, FilterDto filterDto) {
        Page<Appointment> appointments = this.appointmentRepository.findAll(pageable);
        List<Appointment> appointmentsList =  appointments.stream()
                .filter(appointment -> filterByTrainingType(appointment, filterDto.getTrainingTypes()))
                .filter(appointment -> filterByIsIndividual(appointment, filterDto.getIsIndividual()))
                .filter(appointment -> filterByDayOfWeek(appointment, filterDto.getDayOfWeek()))
                .filter(appointment -> appointment.getAvailablePlaces() > 0)
                .collect(Collectors.toList());
        appointments = new PageImpl<>(appointmentsList, pageable, appointmentsList.size());
        return appointments.map(appointment ->{
            AppointmentDto appointmentDto = this.appointmentMapper.appointmentToAppointmentDto(appointment);
            appointmentDto.setTrainingTypeName(appointment.getGymTrainingType().getTrainingType().getName());
            appointmentDto.setGymName(appointment.getGymTrainingType().getGym().getName());
            appointmentDto.setPrice(appointment.getGymTrainingType().getPrice());
            return appointmentDto;
        });
    }

    @Override
    public AppointmentDto add(String authorization, AppointmentDto appointmentDto) {
        ResponseEntity<UserDto> user = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            user = this.userServiceRestTemplate.exchange("/user/id", HttpMethod.GET, request, UserDto.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        if(!user.getBody().getRole().equals("gymmanager"))
            throw new NotFoundException("NISI GYM MANAGER");

        Appointment appointment = this.appointmentMapper.appointmentDtoToAppointment(appointmentDto);
        if(appointment.getGymTrainingType().getTrainingType().isIndividual()){
            appointment.setMaxPeople(1);
            appointment.setAvailablePlaces(1);
        }

        this.appointmentRepository.save(appointment);
        return appointmentDto;
    }

    // Filtriranje po trainingTypeId
    private boolean filterByTrainingType(Appointment appointment, String trainingTypes) {
        return trainingTypes == null || trainingTypes.contains(appointment.getGymTrainingType().getTrainingType().getName());
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
