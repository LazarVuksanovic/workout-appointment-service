package rs.raf.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.userservice.dto.*;

public interface UserService {

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    TokenResponseDto resetPassword(String authorization, ResetPasswordDto resetPasswordDto);
    UserDto update(String authorization, UserUpdateDto userUpdateDto);
    BannedUserDto banUser(String authorization, BannedUserDto bannedUserDto);
    BannedUserDto unbanUser(String authorization, BannedUserDto bannedUserDto);
    UserDto userId(String authorization);
    UserDto onlyAdmin(String authorization);
    Page<UserDto> findAll(Pageable pageable, String authorization);
    UserDto emailVerification(String authorization, String verificationToken);
}
