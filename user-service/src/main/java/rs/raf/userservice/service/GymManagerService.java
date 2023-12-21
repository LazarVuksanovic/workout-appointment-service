package rs.raf.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;

public interface GymManagerService {

    Page<GymManagerDto> findAll(Pageable pageable);
    GymManagerDto add(GymManagerCreateDto gymManagerCreateDto);
}
