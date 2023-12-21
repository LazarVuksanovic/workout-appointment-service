package rs.raf.userservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.userservice.domain.GymManager;
import rs.raf.userservice.dto.ClientDto;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;

import java.time.LocalDate;

@Component
public class GymManagerMapper {

    public GymManagerDto gymManagerToGymManagerDto(GymManager gymManager){
        GymManagerDto gymManagerDto = new GymManagerDto();
        gymManagerDto.setId(gymManager.getId());
        gymManagerDto.setEmail(gymManager.getEmail());
        gymManagerDto.setFirstName(gymManager.getFirstName());
        gymManagerDto.setLastName(gymManager.getLastName());
        gymManagerDto.setUsername(gymManager.getUsername());
        gymManagerDto.setDateOfBirth(gymManager.getDateOfBirth());
        gymManagerDto.setGymName(gymManager.getGymName());
        gymManagerDto.setEmploymentDate(gymManager.getEmploymentDate());
        return gymManagerDto;
    }

    public GymManager gymManagerCreateDtoToGymManager(GymManagerCreateDto gymManagerCreateDto){
        GymManager gymManager = new GymManager();
        gymManager.setEmail(gymManagerCreateDto.getEmail());
        gymManager.setPassword(gymManagerCreateDto.getPassword());
        gymManager.setFirstName(gymManagerCreateDto.getFirstName());
        gymManager.setLastName(gymManagerCreateDto.getLastName());
        gymManager.setUsername(gymManagerCreateDto.getUsername());
        gymManager.setDateOfBirth(gymManagerCreateDto.getDateOfBirth());
        gymManager.setGymName(gymManagerCreateDto.getGymName());
        gymManager.setEmploymentDate(LocalDate.now());
        gymManager.setRole("gymmanager");
        return gymManager;
    }
}
