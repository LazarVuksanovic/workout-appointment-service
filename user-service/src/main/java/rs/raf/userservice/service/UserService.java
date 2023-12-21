package rs.raf.userservice.service;

import rs.raf.userservice.dto.TokenRequestDto;
import rs.raf.userservice.dto.TokenResponseDto;

public interface UserService {

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}
