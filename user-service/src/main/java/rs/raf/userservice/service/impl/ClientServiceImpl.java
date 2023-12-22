package rs.raf.userservice.service.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.userservice.domain.Client;
import rs.raf.userservice.dto.*;
import rs.raf.userservice.exception.NotFoundException;
import rs.raf.userservice.mapper.ClientMapper;
import rs.raf.userservice.repository.ClientRepository;
import rs.raf.userservice.security.service.TokenService;
import rs.raf.userservice.service.ClientService;

import java.util.ArrayList;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;
    private TokenService tokenService;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, TokenService tokenService){
        this.clientRepository = clientRepository;
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
}
