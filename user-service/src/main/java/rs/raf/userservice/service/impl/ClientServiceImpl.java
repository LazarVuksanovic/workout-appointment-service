package rs.raf.userservice.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.userservice.domain.Client;
import rs.raf.userservice.dto.ClientCreateDto;
import rs.raf.userservice.dto.ClientDto;
import rs.raf.userservice.mapper.ClientMapper;
import rs.raf.userservice.repository.ClientRepository;
import rs.raf.userservice.service.ClientService;

import java.util.ArrayList;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {


    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper){
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
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
