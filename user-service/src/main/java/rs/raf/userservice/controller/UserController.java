package rs.raf.userservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.userservice.dto.*;
import rs.raf.userservice.security.CheckSecurity;
import rs.raf.userservice.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation(value = "Login user")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginClient(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        return new ResponseEntity<>(this.userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Reset password")
    @PostMapping("/reset-password")
    public ResponseEntity<TokenResponseDto> resetPassword(@RequestHeader("Authorization") String authorization,
                                                          ResetPasswordDto resetPasswordDto){
        return new ResponseEntity<>(this.userService.resetPassword(authorization, resetPasswordDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Edit user")
    @PostMapping("/edit")
    public ResponseEntity<UserDto> updateClient(@RequestHeader("Authorization") String authorization,
                                                @RequestBody @Valid UserUpdateDto userUpdateDto){
        return new ResponseEntity<>(this.userService.update(authorization, userUpdateDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Ban user")
    @PostMapping("/ban")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<BannedUserDto> banUser(@RequestHeader("Authorization") String authorization,
                                                 @RequestBody @Valid BannedUserDto bannedUserDto){
        return new ResponseEntity<>(this.userService.banUser(authorization, bannedUserDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Unban user")
    @PostMapping("/unban")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<BannedUserDto> unbanUser(@RequestHeader("Authorization") String authorization,
                                                 @RequestBody @Valid BannedUserDto bannedUserDto){
        return new ResponseEntity<>(this.userService.unbanUser(authorization, bannedUserDto), HttpStatus.OK);
    }

    @ApiOperation(value = "User id from token")
    @GetMapping("/id")
    @CheckSecurity(roles = {"admin", "client", "gymmanager"})
    public ResponseEntity<UserDto> userId(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(this.userService.userId(authorization), HttpStatus.OK);
    }

    @ApiOperation(value = "Checks if admin")
    @GetMapping("/only-admin")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<UserDto> onlyAdmin(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(this.userService.onlyAdmin(authorization), HttpStatus.OK);
    }
}
