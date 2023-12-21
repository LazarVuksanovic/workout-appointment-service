package rs.raf.userservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.userservice.domain.GymManager;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;
import rs.raf.userservice.mapper.GymManagerMapper;
import rs.raf.userservice.repository.GymManagerRepository;
import rs.raf.userservice.service.GymManagerService;

@Service
@Transactional
public class GymManagerServiceImpl implements GymManagerService {

    private GymManagerRepository gymManagerRepository;
    private GymManagerMapper gymManagerMapper;

    public GymManagerServiceImpl(GymManagerRepository gymManagerRepository, GymManagerMapper gymManagerMapper){
        this.gymManagerRepository = gymManagerRepository;
        this.gymManagerMapper = gymManagerMapper;
    }

    @Override
    public Page<GymManagerDto> findAll(Pageable pageable) {
        return this.gymManagerRepository.findAll(pageable).map(gymManagerMapper::gymManagerToGymManagerDto);
    }

    @Override
    public GymManagerDto add(GymManagerCreateDto gymManagerCreateDto) {
        GymManager gymManager = this.gymManagerMapper.gymManagerCreateDtoToGymManager(gymManagerCreateDto);
        this.gymManagerRepository.save(gymManager);
        return this.gymManagerMapper.gymManagerToGymManagerDto(gymManager);
    }
}
