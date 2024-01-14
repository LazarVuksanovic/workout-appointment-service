package rs.raf.appointmentservice.service;

import org.springframework.data.domain.Page;
import rs.raf.appointmentservice.domain.GymTrainingType;
import rs.raf.appointmentservice.dto.*;

public interface GymService {

    GymDto getGym(String authorization, Long id);
    GymDto update(String authorization, Long id, GymUpdateDto gymUpdateDto);
    GymTrainingTypeDto addTrainingType(String authorization, Long id, NewGymTrainingTypeDto newGymTrainingTypeDto);
    GymTrainingTypeDto removeTrainingType(String authorization, Long id, Long trainingTypeId);
}
