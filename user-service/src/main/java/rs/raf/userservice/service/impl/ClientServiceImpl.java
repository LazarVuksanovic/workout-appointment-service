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
import rs.raf.userservice.domain.Client;
import rs.raf.userservice.dto.*;
import rs.raf.userservice.exception.NotFoundException;
import rs.raf.userservice.mapper.ClientMapper;
import rs.raf.userservice.mapper.UserMapper;
import rs.raf.userservice.repository.ClientRepository;
import rs.raf.userservice.repository.UserRepository;
import rs.raf.userservice.security.service.TokenService;
import rs.raf.userservice.service.ClientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private ClientMapper clientMapper;
    private UserMapper userMapper;
    private TokenService tokenService;
    private RestTemplate messageServiceRestTemplate;

    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository,
                             ClientMapper clientMapper, TokenService tokenService, UserMapper userMapper,
                             RestTemplate messageServiceRestTemplate){
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.messageServiceRestTemplate = messageServiceRestTemplate;
    }

    @Override
    public Page<ClientDto> findAll(Pageable pageable) {
        return this.clientRepository.findAll(pageable).map(clientMapper::clientToClientDto);
    }

    @Override
    public ClientDto add(ClientCreateDto clientCreateDto) {
        Client client = this.clientMapper.clientCreateDtoToClient(clientCreateDto);
        client.setVerified(false);
        this.clientRepository.save(client);

        //pravimo token
        Claims claims = Jwts.claims();
        claims.put("id", client.getId());
        claims.put("role", client.getRole());
        String authorization = this.tokenService.generate(claims);

        //pravim poruku za aktivaciju mejla i saljemo
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            //pravimo poruku
            MessageCreateDto messageCreateDto = new MessageCreateDto("EMAIL_ACTIVATION", client.getId(), client.getFirstName(), LocalDate.now(), LocalTime.now(), "", client.getEmail(), LocalDateTime.now(), client.getId().toString());

            HttpEntity<MessageCreateDto> request = new HttpEntity<>(messageCreateDto, headers);
            this.messageServiceRestTemplate.exchange("/message", HttpMethod.POST, request, MessageCreateDto.class);
        }catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        return this.clientMapper.clientToClientDto(client);
    }

    @Override
    public IdDto scheduleAppointment(String authorization) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        Client client = this.clientRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        client.setScheduledTrainings(client.getScheduledTrainings()+1);
        this.clientRepository.save(client);
        IdDto idDto = new IdDto();
        idDto.setId(client.getId());
        return idDto;
    }

    @Override
    public IdDto cancelAppointment(String authorization) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        Client client = this.clientRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        if(client.getScheduledTrainings() > 0)
            client.setScheduledTrainings(client.getScheduledTrainings()-1);
        this.clientRepository.save(client);
        IdDto idDto = new IdDto();
        idDto.setId(client.getId());
        return idDto;
    }
    @Override
    public UserDto managerCancelAppointment(String authorization, Integer userId) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")));
        Client client = this.clientRepository
                .findById(userId.longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        if(client.getScheduledTrainings() > 0)
            client.setScheduledTrainings(client.getScheduledTrainings()-1);
        this.clientRepository.save(client);
        UserDto userDtoRes = this.userMapper.userToUserDto(client);
        userDtoRes.setId(client.getId());
        return userDtoRes;
    }
}
