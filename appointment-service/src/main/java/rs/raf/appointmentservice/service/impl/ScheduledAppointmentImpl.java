package rs.raf.appointmentservice.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.raf.appointmentservice.client.messageservice.dto.MessageCreateDto;
import rs.raf.appointmentservice.client.userservice.dto.IdDto;
import rs.raf.appointmentservice.client.userservice.dto.UserDto;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduledAppointmentImpl implements ScheduledAppointmentService {

    private AppointmentRepository appointmentRepository;
    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private ScheduledAppointmentMapper scheduledAppointmentMapper;
    private RestTemplate userServiceRestTemplate;
    private RestTemplate messageServiceRestTemplate;

    public ScheduledAppointmentImpl(AppointmentRepository appointmentRepository,
                                    ScheduledAppointmentRepository scheduledAppointmentRepository,
                                    ScheduledAppointmentMapper scheduledAppointmentMapper,
                                    RestTemplate userServiceRestTemplate, RestTemplate messageServiceRestTemplate){
        this.appointmentRepository = appointmentRepository;
        this.scheduledAppointmentRepository = scheduledAppointmentRepository;
        this.scheduledAppointmentMapper = scheduledAppointmentMapper;
        this.userServiceRestTemplate = userServiceRestTemplate;
        this.messageServiceRestTemplate = messageServiceRestTemplate;
    }

    @Override
    public ScheduledAppointmentDto scheduleAppointment(String authorization, IdDto appointmentId) {
        //dohvatamo trazeni termin
        Optional<Appointment> appointment = this.appointmentRepository.findById(appointmentId.getId());

        //proveravamo da li ima slobodnih mesta
        if(appointment.get().getAvailablePlaces() <= 0)
            throw new AppointmentNotAvailableException("APPOINTMENT NOT AVAILABLE");

        //nalazimo prvo korisnika da bi mogli da proverimo da li je korisnik vec zakazao taj termin
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

        //proveravamo da li je korisnik vec zakazao ovaj termin
        Optional<ScheduledAppointment> s =
                this.scheduledAppointmentRepository.findById(new ScheduledAppointmentId(user.getBody().getId(), appointmentId.getId()));
        if(s.isPresent())
            throw new AlreadyScheduledException("KORISNIK JE VEC ZAKAZAO OVAJ TERMIN");

        //komuniciramo sa user-service da povecamo broj zakazanih treninga korisniku
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/client/schedule-appointment", HttpMethod.POST, request, IdDto.class);
        }catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        //smanjujemo broj slobodnih mesta u terminu
        appointment.get().setAvailablePlaces(appointment.get().getAvailablePlaces()-1);
        this.appointmentRepository.save(appointment.get());

        //pravimo zakazan termin i cuvamo u bazu
        ScheduledAppointmentDto scheduledAppointmentDto = new ScheduledAppointmentDto(user.getBody().getId(), appointmentId.getId());
        this.scheduledAppointmentRepository.save(this.scheduledAppointmentMapper
                .scheduledAppointmentDtoToScheduledAppointment(scheduledAppointmentDto));

        //pravim poruku i saljemo
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            //pravimo poruku
            MessageCreateDto messageCreateDto = new MessageCreateDto();
            messageCreateDto.setMessageType("SUCCESSFULLY_SCHEDULED");
            messageCreateDto.setUserId(user.getBody().getId());
            messageCreateDto.setEmail(user.getBody().getEmail());
            messageCreateDto.setTimeSent(LocalDateTime.now());
            messageCreateDto.setAppointmentTime(appointment.get().getStart());
            messageCreateDto.setAppointmentDate(appointment.get().getDate());
            messageCreateDto.setAppointmentPlace(appointment.get().getGym().getName());
            messageCreateDto.setFirstName(user.getBody().getFirstName());

            HttpEntity<MessageCreateDto> request = new HttpEntity<>(messageCreateDto, headers);
            this.messageServiceRestTemplate.exchange("/message", HttpMethod.POST, request, MessageCreateDto.class);
        }catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        return scheduledAppointmentDto;
    }

    @Override
    public ScheduledAppointmentDto cancelAppointment(String authorization, IdDto appointmentId) {
        //nalazimo prvo id korisnika da bi mogli da nadjemo taj zakazani termin
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
        //proveravamo ko je otkazao termin
        Optional<Appointment> appointment = this.appointmentRepository.findById(appointmentId.getId());
        if(user.getBody().getRole().equals("gymmanager"))
            return managerCancelAppointment(authorization, appointment.get(), user.getBody().getId());

        appointment.get().setAvailablePlaces(appointment.get().getAvailablePlaces()+1);

        //nalazimo taj zakazani termin
        Optional<ScheduledAppointment> scheduledAppointment =
                this.scheduledAppointmentRepository.findById(new ScheduledAppointmentId(user.getBody().getId(),appointmentId.getId()));

        //u slucaju da ne postoji
        if(scheduledAppointment.isEmpty())
            throw new NotFoundException("KORISNIK NIJE ZAKAZAO TAJ TERMIN");

        //komuniciramo sa user-service da dobijemo id korisnika preko tokena
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/client/cancel-appointment", HttpMethod.POST, request, IdDto.class);

        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        //pravim poruku i saljemo
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            //pravimo poruku
            MessageCreateDto messageCreateDto = new MessageCreateDto();
            messageCreateDto.setMessageType("CANCELED_APPOINTMENT");
            messageCreateDto.setUserId(user.getBody().getId());
            messageCreateDto.setEmail(user.getBody().getEmail());
            messageCreateDto.setTimeSent(LocalDateTime.now());
            messageCreateDto.setAppointmentTime(appointment.get().getStart());
            messageCreateDto.setAppointmentDate(appointment.get().getDate());
            messageCreateDto.setAppointmentPlace(appointment.get().getGym().getName());
            messageCreateDto.setFirstName(user.getBody().getFirstName());

            HttpEntity<MessageCreateDto> request = new HttpEntity<>(messageCreateDto, headers);
            this.messageServiceRestTemplate.exchange("/message", HttpMethod.POST, request, MessageCreateDto.class);
        }catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        //brisemo ga
        this.scheduledAppointmentRepository.delete(scheduledAppointment.get());
        return this.scheduledAppointmentMapper.scheduledAppointmentToScheduledAppointmentDto(scheduledAppointment.get());
    }

    private ScheduledAppointmentDto managerCancelAppointment(String authorization, Appointment appointment, Long managerId){
        appointment.setAvailablePlaces(0);
        this.appointmentRepository.save(appointment);

        //svim korisnicima smanjujemo broj zakazanih treninga i brisemo taj scheduledAppointment
        List<ScheduledAppointment> scheduledAppointments =  this.scheduledAppointmentRepository.findAllByAppointmentId(appointment.getId());
        scheduledAppointments.stream().forEach(scheduledAppointment -> {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            // HTTP zahtev za smanjivanje broja zakazanih treninga korisnika
            HttpEntity<IdDto> request = new HttpEntity<>(headers);
            ResponseEntity<UserDto> user =  this.userServiceRestTemplate.exchange("/user/client/"+scheduledAppointment.getId().getUserId()+"/cancel-appointment", HttpMethod.POST, request, UserDto.class);

            //pravim poruku i saljemo
            headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            MessageCreateDto messageCreateDto = new MessageCreateDto();
            messageCreateDto.setMessageType("CANCELED_APPOINTMENT");
            messageCreateDto.setUserId(user.getBody().getId());
            messageCreateDto.setEmail(user.getBody().getEmail());
            messageCreateDto.setTimeSent(LocalDateTime.now());
            messageCreateDto.setAppointmentTime(appointment.getStart());
            messageCreateDto.setAppointmentDate(appointment.getDate());
            messageCreateDto.setAppointmentPlace(appointment.getGym().getName());
            messageCreateDto.setFirstName(user.getBody().getFirstName());

            HttpEntity<MessageCreateDto> request2 = new HttpEntity<>(messageCreateDto, headers);
            this.messageServiceRestTemplate.exchange("/message", HttpMethod.POST, request2, MessageCreateDto.class);

            //brisemo zakazan termin izmedju korisnika i appointmenta
            this.scheduledAppointmentRepository.delete(scheduledAppointment);
        });

        // Vraćamo odgovor na osnovu uspešno obavljenih akcija
        ScheduledAppointmentDto scheduledAppointmentDto = new ScheduledAppointmentDto();
        scheduledAppointmentDto.setAppointmentId(appointment.getId());
        scheduledAppointmentDto.setUserId(managerId);
        return scheduledAppointmentDto;
    }
}
