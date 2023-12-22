package rs.raf.userservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.userservice.domain.Client;
import rs.raf.userservice.domain.User;
import rs.raf.userservice.dto.ClientCreateDto;
import rs.raf.userservice.dto.ClientDto;
import rs.raf.userservice.dto.UserDto;
import rs.raf.userservice.dto.UserUpdateDto;

import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class UserMapper {

    public UserDto userToUserDto(User user) {
        UserDto clientDto = new UserDto();
        clientDto.setId(user.getId());
        clientDto.setEmail(user.getEmail());
        clientDto.setFirstName(user.getFirstName());
        clientDto.setLastName(user.getLastName());
        clientDto.setUsername(user.getUsername());
        clientDto.setDateOfBirth(user.getDateOfBirth());
        return clientDto;
    }

    public void UserUpdateDtoToUser(User user, UserUpdateDto userUpdateDto){
        user.setUsername(userUpdateDto.getUsername());
        user.setPassword(userUpdateDto.getPassword());
        user.setEmail(userUpdateDto.getEmail());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setDateOfBirth(userUpdateDto.getDateOfBirth());
    }
}
