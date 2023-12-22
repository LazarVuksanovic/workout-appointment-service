package rs.raf.userservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.userservice.domain.GymManager;
import rs.raf.userservice.domain.User;
import rs.raf.userservice.dto.*;
import rs.raf.userservice.exception.NotFoundException;
import rs.raf.userservice.mapper.UserMapper;
import rs.raf.userservice.repository.GymManagerRepository;
import rs.raf.userservice.repository.UserRepository;
import rs.raf.userservice.security.service.TokenService;
import rs.raf.userservice.service.UserService;

import java.time.LocalDate;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = this.userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        //Generate token
        return new TokenResponseDto(this.tokenService.generate(claims));
    }

    @Override
    public UserDto update(String authorization, UserUpdateDto userUpdateDto) {
        Claims claims = this.tokenService.parseToken(authorization);
        User user = this.userRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));

        user.setUsername(userUpdateDto.getUsername());
        user.setPassword(userUpdateDto.getPassword());
        user.setEmail(userUpdateDto.getEmail());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setDateOfBirth(userUpdateDto.getDateOfBirth());

        if(user instanceof GymManager){
           GymManager gm = (GymManager) user;
           gm.setGymName(userUpdateDto.getGymName());
           gm.setEmploymentDate(userUpdateDto.getEmploymentDate());
        }

        this.userRepository.save(user);
        return this.userMapper.userToUserDto(user);
    }
}