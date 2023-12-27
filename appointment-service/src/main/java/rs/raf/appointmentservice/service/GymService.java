package rs.raf.appointmentservice.service;

import rs.raf.appointmentservice.domain.GymTrainingType;
import rs.raf.appointmentservice.dto.GymDto;
import rs.raf.appointmentservice.dto.GymTrainingTypeDto;
import rs.raf.appointmentservice.dto.GymUpdateDto;
import rs.raf.appointmentservice.dto.NewGymTrainingTypeDto;

public interface GymService {

    GymDto update(String authorization, Long id, GymUpdateDto gymUpdateDto);
    GymTrainingTypeDto addTrainingType(String authorization, Long id, NewGymTrainingTypeDto newGymTrainingTypeDto);
    GymTrainingTypeDto removeTrainingType(String authorization, Long id, Long trainingTypeId);
}
