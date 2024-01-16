package rs.raf.appointmentservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.appointmentservice.domain.GymTrainingType;
import rs.raf.appointmentservice.dto.GymTrainingTypeDto;
import rs.raf.appointmentservice.repository.TrainingTypeRepository;

@Component
public class GymTrainingTypeMapper {

    public GymTrainingTypeDto gymTrainingTypeToGymTrainingTypeDto(GymTrainingType gymTrainingType){
        GymTrainingTypeDto gymTrainingTypeDto = new GymTrainingTypeDto();
        gymTrainingTypeDto.setId(gymTrainingType.getId());
        gymTrainingTypeDto.setGymId(gymTrainingType.getGym().getId());
        gymTrainingTypeDto.setTrainingTypeId(gymTrainingType.getTrainingType().getId());
        gymTrainingTypeDto.setPrice(gymTrainingType.getPrice());
        gymTrainingTypeDto.setTrainingTypeName(gymTrainingType.getTrainingType().getName());
        return gymTrainingTypeDto;
    }
}
