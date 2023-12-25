package rs.raf.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.userservice.dto.*;

import java.util.ArrayList;

public interface ClientService {

    Page<ClientDto> findAll(Pageable pageable);
    ClientDto add(ClientCreateDto clientCreateDto);
    RoleDto scheduleAppointment(String authorization);
    RoleDto cancelAppointment(String authorization);
}
