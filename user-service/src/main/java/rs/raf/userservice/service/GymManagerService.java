package rs.raf.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;
import rs.raf.userservice.dto.GymNameDto;
import rs.raf.userservice.dto.IdDto;

public interface GymManagerService {

    Page<GymManagerDto> findAll(Pageable pageable);
    GymManagerDto add(GymManagerCreateDto gymManagerCreateDto);
    IdDto checkIfGymManager(String authorization);
    GymManagerDto changeGymName(String authorization, GymNameDto gymNameDto);
    GymManagerDto getByGymName(String authorization, String gymName);
}
