package rs.raf.userservice.service.impl;


import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.userservice.domain.Client;
import rs.raf.userservice.dto.*;
import rs.raf.userservice.exception.NotFoundException;
import rs.raf.userservice.mapper.ClientMapper;
import rs.raf.userservice.repository.ClientRepository;
import rs.raf.userservice.repository.UserRepository;
import rs.raf.userservice.security.service.TokenService;
import rs.raf.userservice.service.ClientService;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private ClientMapper clientMapper;
    private TokenService tokenService;

    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository, ClientMapper clientMapper, TokenService tokenService){
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
        this.tokenService = tokenService;
    }

    @Override
    public Page<ClientDto> findAll(Pageable pageable) {
        return this.clientRepository.findAll(pageable).map(clientMapper::clientToClientDto);
    }

    @Override
    public ClientDto add(ClientCreateDto clientCreateDto) {
        Client client = this.clientMapper.clientCreateDtoToClient(clientCreateDto);
        this.clientRepository.save(client);
        return this.clientMapper.clientToClientDto(client);
    }

    @Override
    public RoleDto scheduleAppointment(String authorization) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        Client client = this.clientRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        client.setScheduledTrainings(client.getScheduledTrainings()+1);
        this.clientRepository.save(client);
        RoleDto roleDto = new RoleDto();
        roleDto.setId(client.getId());
        roleDto.setRole(client.getRole());
        return roleDto;
    }

    @Override
    public RoleDto cancelAppointment(String authorization) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        Client client = this.clientRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        if(client.getScheduledTrainings() > 0)
            client.setScheduledTrainings(client.getScheduledTrainings()-1);
        this.clientRepository.save(client);
        RoleDto roleDto = new RoleDto();
        roleDto.setId(client.getId());
        roleDto.setRole(client.getRole());
        return roleDto;
    }
    @Override
    public RoleDto managerCancelAppointment(String authorization, Integer userId) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")));
        Client client = this.clientRepository
                .findById(userId.longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        if(client.getScheduledTrainings() > 0)
            client.setScheduledTrainings(client.getScheduledTrainings()-1);
        this.clientRepository.save(client);
        RoleDto roleDtoRes = new RoleDto();
        roleDtoRes.setId(client.getId());
        roleDtoRes.setRole(client.getRole());
        return roleDtoRes;
    }
}
