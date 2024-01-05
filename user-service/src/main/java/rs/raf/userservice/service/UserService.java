package rs.raf.userservice.service;

import rs.raf.userservice.dto.*;

public interface UserService {

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    UserDto update(String authorization, UserUpdateDto userUpdateDto);
    BannedUserDto banUser(String authorization, BannedUserDto bannedUserDto);
    BannedUserDto unbanUser(String authorization, BannedUserDto bannedUserDto);
    UserDto userId(String authorization);
}
