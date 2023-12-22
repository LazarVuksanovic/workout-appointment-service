package rs.raf.userservice.service;

import rs.raf.userservice.dto.TokenRequestDto;
import rs.raf.userservice.dto.TokenResponseDto;
import rs.raf.userservice.dto.UserDto;
import rs.raf.userservice.dto.UserUpdateDto;

public interface UserService {

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    UserDto update(String authorization, UserUpdateDto userUpdateDto);
}
