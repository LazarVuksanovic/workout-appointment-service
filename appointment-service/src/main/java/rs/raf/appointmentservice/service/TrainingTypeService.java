package rs.raf.appointmentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.appointmentservice.dto.TrainingTypeDto;

public interface TrainingTypeService {

    Page<TrainingTypeDto> findAll(Pageable pageable, String authorization);
    TrainingTypeDto trainingType(String authorization, Long id);
}
