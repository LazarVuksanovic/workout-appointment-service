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
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setRole(user.getRole());
        userDto.setVerified(user.getVerified());
        return userDto;
    }

    public void UserUpdateDtoToUser(User user, UserUpdateDto userUpdateDto){
        user.setUsername(userUpdateDto.getUsername());
        user.setEmail(userUpdateDto.getEmail());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setDateOfBirth(userUpdateDto.getDateOfBirth());
        user.setVerified(user.getVerified());
    }
}
