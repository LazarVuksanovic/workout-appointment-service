package rs.raf.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;
import rs.raf.userservice.dto.RoleDto;

public interface GymManagerService {

    Page<GymManagerDto> findAll(Pageable pageable);
    GymManagerDto add(GymManagerCreateDto gymManagerCreateDto);
    RoleDto checkIfGymManager(String authorization);
}
