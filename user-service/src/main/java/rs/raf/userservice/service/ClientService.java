package rs.raf.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.userservice.dto.ClientCreateDto;
import rs.raf.userservice.dto.ClientDto;
import rs.raf.userservice.dto.TokenRequestDto;
import rs.raf.userservice.dto.TokenResponseDto;

import java.util.ArrayList;

public interface ClientService {

    Page<ClientDto> findAll(Pageable pageable);
    ClientDto add(ClientCreateDto clientCreateDto);
}
