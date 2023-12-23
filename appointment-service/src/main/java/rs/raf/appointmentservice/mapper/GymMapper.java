package rs.raf.appointmentservice.mapper;


import org.springframework.stereotype.Component;
import rs.raf.appointmentservice.domain.Gym;
import rs.raf.appointmentservice.dto.GymDto;

@Component
public class GymMapper {

    public GymDto gymToGymDto(Gym gym){
        GymDto gymDto = new GymDto();
        gymDto.setId(gym.getId());
        gymDto.setName(gym.getName());;
        gymDto.setDescription(gym.getDescription());
        gymDto.setNumOfPersonalCoaches(gym.getNumOfPersonalCoaches());
        return gymDto;
    }
}
