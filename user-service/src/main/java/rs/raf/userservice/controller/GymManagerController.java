package rs.raf.userservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;
import rs.raf.userservice.dto.RoleDto;
import rs.raf.userservice.security.CheckSecurity;
import rs.raf.userservice.service.GymManagerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/gymmanager")
public class GymManagerController {

    private GymManagerService gymManagerService;

    public GymManagerController(GymManagerService gymManagerService){ this.gymManagerService = gymManagerService; }

    @ApiOperation(value = "Find all gym managers")
    @GetMapping
    public ResponseEntity<Page<GymManagerDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(this.gymManagerService.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Register gym manager")
    @PostMapping
    public ResponseEntity<GymManagerDto> saveGymManager(@RequestBody @Valid GymManagerCreateDto gymManagerCreateDto){
        return new ResponseEntity<>(this.gymManagerService.add(gymManagerCreateDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Check if gym manager")
    @GetMapping("/check-role")
    @CheckSecurity(roles = {"admin", "gymmanager"})
    public ResponseEntity<RoleDto> checkIfGymManager(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(this.gymManagerService.checkIfGymManager(authorization), HttpStatus.OK);
    }
}
