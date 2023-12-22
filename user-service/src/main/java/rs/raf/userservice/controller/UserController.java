package rs.raf.userservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.userservice.dto.TokenRequestDto;
import rs.raf.userservice.dto.TokenResponseDto;
import rs.raf.userservice.dto.UserDto;
import rs.raf.userservice.dto.UserUpdateDto;
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

    @ApiOperation(value = "Edit user")
    @PostMapping("/edit")
    public ResponseEntity<UserDto> updateClient(@RequestHeader("Authorization") String authorization,
                                                @RequestBody @Valid UserUpdateDto userUpdateDto){
        return new ResponseEntity<>(this.userService.update(authorization, userUpdateDto), HttpStatus.OK);
    }
}
