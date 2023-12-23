package rs.raf.appointmentservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.appointmentservice.domain.Gym;
import rs.raf.appointmentservice.dto.GymDto;
import rs.raf.appointmentservice.dto.GymUpdateDto;
import rs.raf.appointmentservice.exception.NotFoundException;
import rs.raf.appointmentservice.mapper.GymMapper;
import rs.raf.appointmentservice.repository.GymRepository;
import rs.raf.appointmentservice.service.GymService;

@Service
@Transactional
public class GymServiceImpl implements GymService {

    private GymRepository gymRepository;
    private GymMapper gymMapper;

    public GymServiceImpl(GymRepository gymRepository, GymMapper gymMapper){
        this.gymRepository = gymRepository;
        this.gymMapper = gymMapper;
    }

    @Override
    public GymDto update(Long id, GymUpdateDto gymUpdateDto) {
        Gym gym = this.gymRepository.findById(id).orElseThrow(() -> new NotFoundException("greska"));
        gym.setName(gymUpdateDto.getName());
        gym.setDescription(gymUpdateDto.getDescription());
        gym.setNumOfPersonalCoaches(gymUpdateDto.getNumOfPersonalCoaches());
        return this.gymMapper.gymToGymDto(gym);
    }
}
