package rs.raf.appointmentservice.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.raf.appointmentservice.client.userservice.dto.IdDto;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.domain.ScheduledAppointment;
import rs.raf.appointmentservice.domain.ScheduledAppointmentId;
import rs.raf.appointmentservice.dto.ScheduledAppointmentDto;
import rs.raf.appointmentservice.exception.AlreadyScheduledException;
import rs.raf.appointmentservice.exception.AppointmentNotAvailableException;
import rs.raf.appointmentservice.exception.NotFoundException;
import rs.raf.appointmentservice.mapper.ScheduledAppointmentMapper;
import rs.raf.appointmentservice.repository.AppointmentRepository;
import rs.raf.appointmentservice.repository.ScheduledAppointmentRepository;
import rs.raf.appointmentservice.service.ScheduledAppointmentService;

import java.util.Optional;

@Service
@Transactional
public class ScheduledAppointmentImpl implements ScheduledAppointmentService {

    private AppointmentRepository appointmentRepository;
    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private ScheduledAppointmentMapper scheduledAppointmentMapper;
    private RestTemplate userServiceRestTemplate;

    public ScheduledAppointmentImpl(AppointmentRepository appointmentRepository,
                                    ScheduledAppointmentRepository scheduledAppointmentRepository,
                                    ScheduledAppointmentMapper scheduledAppointmentMapper,
                                    RestTemplate userServiceRestTemplate){
        this.appointmentRepository = appointmentRepository;
        this.scheduledAppointmentRepository = scheduledAppointmentRepository;
        this.scheduledAppointmentMapper = scheduledAppointmentMapper;
        this.userServiceRestTemplate = userServiceRestTemplate;
    }

    @Override
    public ScheduledAppointmentDto scheduleAppointment(String authorization, IdDto appointmentId) {
        //dohvatamo trazeni termin
        Optional<Appointment> appointment = this.appointmentRepository.findById(appointmentId.getId());

        //proveravamo da li ima slobodnih mesta
        if(appointment.get().getAvailablePlaces() <= 0)
            throw new AppointmentNotAvailableException("APPOINTMENT NOT AVAILABLE");

        //nalazimo prvo id korisnika da bi mogli da proverimo da li je korisnik vec zakazao taj termin
        ResponseEntity<IdDto> userId = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            userId = this.userServiceRestTemplate.exchange("/user/id", HttpMethod.GET, request, IdDto.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        //proveravamo da li je korisnik vec zakazao ovaj termin
        Optional<ScheduledAppointment> s =
                this.scheduledAppointmentRepository.findById(new ScheduledAppointmentId(userId.getBody().getId(), appointmentId.getId()));
        if(s.isPresent())
            throw new AlreadyScheduledException("KORISNIK JE VEC ZAKAZAO OVAJ TERMIN");

        //komuniciramo sa user-service da dobijemo id korisnika preko tokena
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            userId = this.userServiceRestTemplate.exchange("/user/client/schedule-appointment", HttpMethod.GET, request, IdDto.class);

        }catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        //smanjujemo broj slobodnih mesta u terminu
        appointment.get().setAvailablePlaces(appointment.get().getAvailablePlaces()-1);
        this.appointmentRepository.save(appointment.get());

        //pravimo zakazan termin i cuvamo u bazu
        ScheduledAppointmentDto scheduledAppointmentDto = new ScheduledAppointmentDto(userId.getBody().getId(), appointmentId.getId());
        this.scheduledAppointmentRepository.save(this.scheduledAppointmentMapper
                .scheduledAppointmentDtoToScheduledAppointment(scheduledAppointmentDto));
        return scheduledAppointmentDto;
    }

    @Override
    public ScheduledAppointmentDto cancelAppointment(String authorization, IdDto appointmentId) {
        //nalazimo prvo id korisnika da bi mogli da nadjemo taj zakazani termin
        ResponseEntity<IdDto> userId = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            userId = this.userServiceRestTemplate.exchange("/user/id", HttpMethod.GET, request, IdDto.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        //nalazimo taj zakazani termin
        Optional<ScheduledAppointment> scheduledAppointment =
                this.scheduledAppointmentRepository.findById(new ScheduledAppointmentId(userId.getBody().getId(),appointmentId.getId()));

        //u slucaju da ne postoji
        if(scheduledAppointment.isEmpty())
            throw new NotFoundException("KORISNIK NIJE ZAKAZAO TAJ TERMIN");

        //komuniciramo sa user-service da dobijemo id korisnika preko tokena
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/client/cancel-appointment", HttpMethod.GET, request, Long.class);

        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        //brisemo ga
        this.scheduledAppointmentRepository.delete(scheduledAppointment.get());
        return this.scheduledAppointmentMapper.scheduledAppointmentToScheduledAppointmentDto(scheduledAppointment.get());
    }
}
