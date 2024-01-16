package rs.raf.appointmentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.appointmentservice.domain.Gym;
import rs.raf.appointmentservice.domain.GymTrainingType;
import rs.raf.appointmentservice.dto.*;

public interface GymService {

    Page<GymDto> findAll(Pageable pageable, String authorization);
    GymDto getGym(String authorization, Long id);
    GymDto getGymByName(String authorization, NameDto name);
    GymDto update(String authorization, Long id, GymUpdateDto gymUpdateDto);
    GymTrainingTypeDto addTrainingType(String authorization, Long id, NewGymTrainingTypeDto newGymTrainingTypeDto);
    GymTrainingTypeDto removeTrainingType(String authorization, Long id);
    Page<GymTrainingTypeDto> findAllTrainingTypes(Pageable pageable, String authorization, Long gymId);
}
