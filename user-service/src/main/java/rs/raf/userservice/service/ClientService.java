package rs.raf.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.userservice.dto.*;

public interface ClientService {

    Page<ClientDto> findAll(Pageable pageable);
    ClientDto add(ClientCreateDto clientCreateDto);
    IdDto scheduleAppointment(String authorization);
    IdDto cancelAppointment(String authorization);
    UserDto managerCancelAppointment(String authorization, Integer userId);
}
