package rs.raf.userservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.userservice.dto.TokenRequestDto;
import rs.raf.userservice.dto.TokenResponseDto;
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
}
