package rs.raf.appointmentservice.service;

import org.springframework.data.domain.Page;
import rs.raf.appointmentservice.dto.TrainingTypeDto;

public interface TrainingTypeService {

    TrainingTypeDto trainingType(String authorization, Long id);
}
