package rs.raf.userservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.userservice.dto.GymManagerCreateDto;
import rs.raf.userservice.dto.GymManagerDto;
import rs.raf.userservice.dto.GymNameDto;
import rs.raf.userservice.dto.IdDto;
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
    public ResponseEntity<IdDto> checkIfGymManager(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(this.gymManagerService.checkIfGymManager(authorization), HttpStatus.OK);
    }

    @ApiOperation(value = "Change gym manager gym name")
    @PostMapping("/gym-name")
    public ResponseEntity<GymManagerDto> changeGymName(@RequestHeader("Authorization") String authorization,
                                                       @RequestBody GymNameDto gymNameDto){
        return new ResponseEntity<>(this.gymManagerService.changeGymName(authorization, gymNameDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Get gym manager by gym name")
    @GetMapping("/get-by-gym-name/{gymName}")
    public ResponseEntity<GymManagerDto> getByGymName(@RequestHeader("Authorization") String authorization,
                                                       @PathVariable String gymName){
        return new ResponseEntity<>(this.gymManagerService.getByGymName(authorization, gymName), HttpStatus.OK);
    }
}
