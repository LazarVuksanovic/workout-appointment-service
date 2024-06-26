package rs.raf.userservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.raf.userservice.client.messageservice.dto.MessageCreateDto;
import rs.raf.userservice.domain.BannedUser;
import rs.raf.userservice.domain.Client;
import rs.raf.userservice.domain.GymManager;
import rs.raf.userservice.domain.User;
import rs.raf.userservice.dto.*;
import rs.raf.userservice.exception.BannedUserException;
import rs.raf.userservice.exception.NotFoundException;
import rs.raf.userservice.mapper.BannedUserMapper;
import rs.raf.userservice.mapper.UserMapper;
import rs.raf.userservice.repository.BannedUserRepository;
import rs.raf.userservice.repository.ClientRepository;
import rs.raf.userservice.repository.GymManagerRepository;
import rs.raf.userservice.repository.UserRepository;
import rs.raf.userservice.security.service.TokenService;
import rs.raf.userservice.service.UserService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;
    private BannedUserRepository bannedUserRepository;
    private BannedUserMapper bannedUserMapper;
    private RestTemplate messageServiceRestTemplate;
    private ClientRepository clientRepository;
    private GymManagerRepository gymManagerRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService,
                           BannedUserRepository bannedUserRepository, BannedUserMapper bannedUserMapper,
                           RestTemplate messageServiceRestTemplate, ClientRepository clientRepository,
                           GymManagerRepository gymManagerRepository){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.bannedUserRepository = bannedUserRepository;
        this.bannedUserMapper = bannedUserMapper;
        this.messageServiceRestTemplate = messageServiceRestTemplate;
        this.clientRepository = clientRepository;
        this.gymManagerRepository = gymManagerRepository;
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = this.userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));

        Optional<BannedUser> bannedUser = this.bannedUserRepository.findById(user.getId());
        if(bannedUser.isPresent())
            throw new BannedUserException("KORISNIK JE BANOVAN");
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        //Generate token
        return new TokenResponseDto(this.tokenService.generate(claims));
    }

    @Override
    public TokenResponseDto resetPassword(String authorization, ResetPasswordDto resetPasswordDto) {
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        User user = this.userRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        if(user.getPassword().equals(resetPasswordDto.getOldPassword())){
            //setujemo novu sifru
            user.setPassword(resetPasswordDto.getNewPassword());
            //pravim poruku za aktivaciju mejla i saljemo
            try{
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", authorization);
                //pravimo poruku
                MessageCreateDto messageCreateDto = new MessageCreateDto("RESET_PASSWORD", user.getId(), user.getFirstName(), LocalDate.now(), LocalTime.now(), "", user.getEmail(), LocalDateTime.now(), "");

                HttpEntity<MessageCreateDto> request = new HttpEntity<>(messageCreateDto, headers);
                this.messageServiceRestTemplate.exchange("/message", HttpMethod.POST, request, MessageCreateDto.class);
            }catch (HttpClientErrorException e) {
                if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                    throw new NotFoundException("NEVALIDAN KORISNIK");
            }
        }
        this.userRepository.save(user);
        return new TokenResponseDto(authorization);
    }


    @Override
    public UserDto update(String authorization, UserUpdateDto userUpdateDto) {
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        User user = this.userRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));

        this.userMapper.UserUpdateDtoToUser(user, userUpdateDto);
        this.userRepository.save(user);
        return this.userMapper.userToUserDto(user);
    }

    @Override
    public BannedUserDto banUser(String authorization, BannedUserDto bannedUserDto) {
        Optional<BannedUser> exists =  this.bannedUserRepository.findById(bannedUserDto.getId());

        if (exists.isPresent())
            throw new BannedUserException("VEC BANOVAN KORISNIK");

        Optional<User> user =  this.userRepository.findById(bannedUserDto.getId());

        if (user.isEmpty())
            throw new NotFoundException("KORISNIK NE POSTOJI");

        this.bannedUserRepository.save(this.bannedUserMapper.bannedUserDtoToBannedUser(bannedUserDto));
        return bannedUserDto;
    }

    @Override
    public BannedUserDto unbanUser(String authorization, BannedUserDto bannedUserDto) {
        Optional<BannedUser> bannedUser =  this.bannedUserRepository.findById(bannedUserDto.getId());

        if (bannedUser.isEmpty())
            throw new NotFoundException("KORISNIK NIJE BANOVAN");

        this.bannedUserRepository.delete(this.bannedUserMapper.bannedUserDtoToBannedUser(bannedUserDto));
        return bannedUserDto;
    }

    @Override
    public UserDto userId(String authorization) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        User user = this.userRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        UserDto userDto = this.userMapper.userToUserDto(user);
        userDto.setBanned(this.bannedUserRepository.findById(user.getId()).isPresent());
        Optional<GymManager> manager = this.gymManagerRepository.findById(user.getId());
        manager.ifPresent(gymManager -> userDto.setGymName(manager.get().getGymName()));
        return userDto;
    }

    @Override
    public UserDto onlyAdmin(String authorization) {
        //ovo sam stelovao zbog Bearer
        Claims claims = this.tokenService.parseToken(authorization.substring(authorization.indexOf(" ")).trim());
        User user = this.userRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        return this.userMapper.userToUserDto(user);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable, String authorization) {
        Page<UserDto> users = this.userRepository.findAll(pageable).map(this.userMapper::userToUserDto);

        users.map(user -> {
            Optional<Client> client = this.clientRepository.findById(user.getId());
            client.ifPresent(value -> user.setScheduledAppointments(value.getScheduledTrainings()));
            user.setBanned(this.bannedUserRepository.findById(user.getId()).isPresent());
            return user;
        });
        return users;
    }

    @Override
    public UserDto emailVerification(String authorization, String verificationToken) {
        Claims claims = this.tokenService.parseToken(verificationToken);
        User user = this.userRepository
                .findById(claims.get("id", Integer.class).longValue())
                .orElseThrow(() -> new NotFoundException("greska"));
        user.setVerified(true);
        return this.userMapper.userToUserDto(user);
    }
}