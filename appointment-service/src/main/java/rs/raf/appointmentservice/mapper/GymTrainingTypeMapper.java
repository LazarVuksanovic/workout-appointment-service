package rs.raf.appointmentservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.appointmentservice.domain.GymTrainingType;
import rs.raf.appointmentservice.dto.GymTrainingTypeDto;

@Component
public class GymTrainingTypeMapper {

    public GymTrainingTypeDto gymTrainingTypeToGymTrainingTypeDto(GymTrainingType gymTrainingType){
        GymTrainingTypeDto gymTrainingTypeDto = new GymTrainingTypeDto();
        gymTrainingTypeDto.setGymId(gymTrainingType.getGym().getId());
        gymTrainingTypeDto.setTrainingTypeId(gymTrainingType.getTrainingType().getId());
        gymTrainingTypeDto.setPrice(gymTrainingType.getPrice());
        return gymTrainingTypeDto;
    }
}
