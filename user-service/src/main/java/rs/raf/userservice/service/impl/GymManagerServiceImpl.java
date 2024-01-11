package rs.raf.userservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.raf.userservice.client.messageservice.dto.MessageCreateDto;
import rs.raf.userservice.domain.GymManager;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;
import rs.raf.userservice.dto.IdDto;
import rs.raf.userservice.exception.NotFoundException;
import rs.raf.userservice.mapper.GymManagerMapper;
import rs.raf.userservice.repository.GymManagerRepository;
import rs.raf.userservice.security.service.TokenService;
import rs.raf.userservice.service.GymManagerService;

import java.time.LocalDateTime;

@Service
@Transactional
public class GymManagerServiceImpl implements GymManagerService {

    private GymManagerRepository gymManagerRepository;
    private GymManagerMapper gymManagerMapper;
    private TokenService tokenService;
    private RestTemplate messageServiceRestTemplate;

    public GymManagerServiceImpl(GymManagerRepository gymManagerRepository, GymManagerMapper gymManagerMapper,
                                 TokenService tokenService, RestTemplate messageServiceRestTemplate){
        this.gymManagerRepository = gymManagerRepository;
        this.gymManagerMapper = gymManagerMapper;
        this.tokenService = tokenService;
        this.messageServiceRestTemplate = messageServiceRestTemplate;
    }

    @Override
    public Page<GymManagerDto> findAll(Pageable pageable) {
        return this.gymManagerRepository.findAll(pageable).map(gymManagerMapper::gymManagerToGymManagerDto);
    }

    @Override
    public GymManagerDto add(GymManagerCreateDto gymManagerCreateDto) {
        GymManager gymManager = this.gymManagerMapper.gymManagerCreateDtoToGymManager(gymManagerCreateDto);

        //pravimo token
        Claims claims = Jwts.claims();
        claims.put("id", gymManager.getId());
        claims.put("role", gymManager.getRole());
        String authorization = this.tokenService.generate(claims);

        //pravim poruku za aktivaciju mejla i saljemo
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            //pravimo poruku
            MessageCreateDto messageCreateDto = new MessageCreateDto();
            messageCreateDto.setMessageType("EMAIL_ACTIVATION");
            messageCreateDto.setUserId(gymManager.getId());
            messageCreateDto.setEmail(gymManager.getEmail());
            messageCreateDto.setTimeSent(LocalDateTime.now());
            messageCreateDto.setFirstName(gymManager.getFirstName());

            HttpEntity<MessageCreateDto> request = new HttpEntity<>(messageCreateDto, headers);
            this.messageServiceRestTemplate.exchange("/message", HttpMethod.POST, request, MessageCreateDto.class);
        }catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        this.gymManagerRepository.save(gymManager);
        return this.gymManagerMapper.gymManagerToGymManagerDto(gymManager);
    }

    @Override
    public IdDto checkIfGymManager(String authorization) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        GymManager gymManager = this.gymManagerRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        IdDto idDto = new IdDto();
        idDto.setId(gymManager.getId());
        return idDto;
    }
}
