package rs.raf.appointmentservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.appointmentservice.domain.TrainingType;
import rs.raf.appointmentservice.dto.TrainingTypeDto;

@Component
public class TrainingTypeMapper {

    public TrainingTypeDto trainingTypeToTrainingTypeDto(TrainingType trainingType){
        TrainingTypeDto trainingTypeDto = new TrainingTypeDto();
        trainingTypeDto.setIndividual(trainingType.isIndividual());
        trainingTypeDto.setId(trainingType.getId());
        trainingTypeDto.setName(trainingType.getName());
        return trainingTypeDto;
    }
}
