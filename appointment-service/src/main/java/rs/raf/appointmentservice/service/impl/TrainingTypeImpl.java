package rs.raf.appointmentservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.appointmentservice.domain.TrainingType;
import rs.raf.appointmentservice.dto.GymTrainingTypeDto;
import rs.raf.appointmentservice.dto.TrainingTypeDto;
import rs.raf.appointmentservice.mapper.GymTrainingTypeMapper;
import rs.raf.appointmentservice.mapper.TrainingTypeMapper;
import rs.raf.appointmentservice.repository.GymTrainingTypeRepository;
import rs.raf.appointmentservice.repository.TrainingTypeRepository;
import rs.raf.appointmentservice.service.TrainingTypeService;

@Service
@Transactional
public class TrainingTypeImpl implements TrainingTypeService {

    private TrainingTypeRepository trainingTypeRepository;
    private TrainingTypeMapper trainingTypeMapper;
    private GymTrainingTypeRepository gymTrainingTypeRepository;
    private GymTrainingTypeMapper gymTrainingTypeMapper;

    public TrainingTypeImpl(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper,
                            GymTrainingTypeRepository gymTrainingTypeRepository,
                            GymTrainingTypeMapper gymTrainingTypeMapper){
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
        this.gymTrainingTypeRepository = gymTrainingTypeRepository;
        this.gymTrainingTypeMapper = gymTrainingTypeMapper;
    }

    @Override
    public Page<TrainingTypeDto> findAll(Pageable pageable, String authorization) {
        return this.trainingTypeRepository.findAll(pageable).map(this.trainingTypeMapper::trainingTypeToTrainingTypeDto);
    }

    @Override
    public TrainingTypeDto trainingType(String authorization, Long id) {
        TrainingType trainingType = this.trainingTypeRepository.findById(id).get();
        TrainingTypeDto trainingTypeDto = new TrainingTypeDto();
        trainingTypeDto.setId(trainingType.getId());
        trainingTypeDto.setName(trainingType.getName());
        trainingTypeDto.setIndividual(trainingType.isIndividual());
        return trainingTypeDto;
    }

    @Override
    public GymTrainingTypeDto price(String authorization, Long gymId, Long typeId) {
        return this.gymTrainingTypeMapper.gymTrainingTypeToGymTrainingTypeDto(this.gymTrainingTypeRepository.findByGymIdAndTrainingTypeId(gymId, typeId).get());
    }
}
