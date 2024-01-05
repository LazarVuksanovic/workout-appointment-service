package rs.raf.userservice.service.impl;

import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.userservice.domain.GymManager;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;
import rs.raf.userservice.dto.IdDto;
import rs.raf.userservice.exception.NotFoundException;
import rs.raf.userservice.mapper.GymManagerMapper;
import rs.raf.userservice.repository.GymManagerRepository;
import rs.raf.userservice.security.service.TokenService;
import rs.raf.userservice.service.GymManagerService;

@Service
@Transactional
public class GymManagerServiceImpl implements GymManagerService {

    private GymManagerRepository gymManagerRepository;
    private GymManagerMapper gymManagerMapper;
    private TokenService tokenService;

    public GymManagerServiceImpl(GymManagerRepository gymManagerRepository, GymManagerMapper gymManagerMapper, TokenService tokenService){
        this.gymManagerRepository = gymManagerRepository;
        this.gymManagerMapper = gymManagerMapper;
        this.tokenService = tokenService;
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

    @Override
    public IdDto checkIfGymManager(String authorization) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        GymManager gymManager = this.gymManagerRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        IdDto idDto = new IdDto();
        idDto.setId(gymManager.getId());
        return idDto;
    }
}
