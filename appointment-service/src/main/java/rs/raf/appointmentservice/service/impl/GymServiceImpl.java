package rs.raf.appointmentservice.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.raf.appointmentservice.client.userservice.dto.IdDto;
import rs.raf.appointmentservice.domain.Gym;
import rs.raf.appointmentservice.domain.GymTrainingType;
import rs.raf.appointmentservice.domain.TrainingType;
import rs.raf.appointmentservice.dto.GymDto;
import rs.raf.appointmentservice.dto.GymTrainingTypeDto;
import rs.raf.appointmentservice.dto.GymUpdateDto;
import rs.raf.appointmentservice.dto.NewGymTrainingTypeDto;
import rs.raf.appointmentservice.exception.NotFoundException;
import rs.raf.appointmentservice.exception.UnauthorizedException;
import rs.raf.appointmentservice.mapper.GymMapper;
import rs.raf.appointmentservice.mapper.GymTrainingTypeMapper;
import rs.raf.appointmentservice.repository.GymRepository;
import rs.raf.appointmentservice.repository.GymTrainingTypeRepository;
import rs.raf.appointmentservice.repository.TrainingTypeRepository;
import rs.raf.appointmentservice.service.GymService;

import java.util.Optional;

@Service
@Transactional
public class GymServiceImpl implements GymService {

    private GymRepository gymRepository;
    private GymTrainingTypeRepository gymTrainingTypeRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private GymMapper gymMapper;
    private GymTrainingTypeMapper gymTrainingTypeMapper;
    private RestTemplate userServiceRestTemplate;

    public GymServiceImpl(GymRepository gymRepository, GymTrainingTypeRepository gymTrainingTypeRepository,
                          TrainingTypeRepository trainingTypeRepository, GymMapper gymMapper,
                          GymTrainingTypeMapper gymTrainingTypeMapper, RestTemplate userServiceRestTemplate){
        this.gymRepository = gymRepository;
        this.gymTrainingTypeRepository = gymTrainingTypeRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.gymMapper = gymMapper;
        this.gymTrainingTypeMapper = gymTrainingTypeMapper;
        this.userServiceRestTemplate = userServiceRestTemplate;
    }

    @Override
    public GymDto getGym(String authorization, Long id) {
        return this.gymMapper.gymToGymDto(this.gymRepository.findById(id).get());
    }

    @Override
    public GymDto update(String authorization, Long id, GymUpdateDto gymUpdateDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/gymmanager/check-role", HttpMethod.GET, request, IdDto.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        Gym gym = this.gymRepository.findById(id).orElseThrow(() -> new NotFoundException("greska"));
        gym.setName(gymUpdateDto.getName());
        gym.setDescription(gymUpdateDto.getDescription());
        gym.setNumOfPersonalCoaches(gymUpdateDto.getNumOfPersonalCoaches());
        return this.gymMapper.gymToGymDto(gym);
    }

    @Override
    public GymTrainingTypeDto addTrainingType(String authorization, Long id, NewGymTrainingTypeDto newGymTrainingTypeDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/gymmanager/check-role", HttpMethod.GET, request, IdDto.class);
        }catch (HttpClientErrorException e){
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED) || e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                throw new UnauthorizedException("Korisnik nema dozvolu za pristup.");
            }
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        Gym gym = this.gymRepository.findById(id).orElseThrow(() -> new NotFoundException("greska"));
        TrainingType trainingType = this.trainingTypeRepository.findById(newGymTrainingTypeDto.getId()).orElseThrow(() -> new NotFoundException("greska"));
        GymTrainingType gymTrainingType = new GymTrainingType();
        gymTrainingType.setGym(gym);
        gymTrainingType.setTrainingType(trainingType);
        gymTrainingType.setPrice(newGymTrainingTypeDto.getPrice());
        this.gymTrainingTypeRepository.save(gymTrainingType);
        return this.gymTrainingTypeMapper.gymTrainingTypeToGymTrainingTypeDto(gymTrainingType);
    }

    @Override
    public GymTrainingTypeDto removeTrainingType(String authorization, Long id, Long trainingTypeId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/gymmanager/check-role", HttpMethod.GET, request, IdDto.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        Optional<GymTrainingType> gymTrainingType = this.gymTrainingTypeRepository.findByGymIdAndTrainingTypeId(id, trainingTypeId);
        this.gymTrainingTypeRepository.delete(gymTrainingType.get());
        return this.gymTrainingTypeMapper.gymTrainingTypeToGymTrainingTypeDto(gymTrainingType.get());
    }
}
