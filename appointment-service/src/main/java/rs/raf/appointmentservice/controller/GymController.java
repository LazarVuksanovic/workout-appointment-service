package rs.raf.appointmentservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.appointmentservice.dto.*;
import rs.raf.appointmentservice.service.GymService;

@RestController
@RequestMapping("/gym")
public class GymController {

    private GymService gymService;

    public GymController(GymService gymService){
        this.gymService = gymService;
    }

    @ApiOperation(value = "Get all gyms")
    @GetMapping()
    public ResponseEntity<Page<GymDto>> findAll(@RequestHeader("Authorization") String authorization,
                                          Pageable pageable){
        return new ResponseEntity<>(this.gymService.findAll(pageable, authorization), HttpStatus.OK);
    }

    @ApiOperation(value = "Get gym")
    @GetMapping("/{id}")
    public ResponseEntity<GymDto> getGym(@RequestHeader("Authorization") String authorization,
                                            @PathVariable Long id){
        return new ResponseEntity<>(this.gymService.getGym(authorization, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get gym by name")
    @PostMapping("/name")
    public ResponseEntity<GymDto> getGymByName(@RequestHeader("Authorization") String authorization,
                                            @RequestBody NameDto name){
        return new ResponseEntity<>(this.gymService.getGymByName(authorization, name), HttpStatus.OK);
    }

    @ApiOperation(value = "Edit gym")
    @PostMapping("/{id}/edit")
    public ResponseEntity<GymDto> updateGym(@RequestHeader("Authorization") String authorization,
                                            @PathVariable Long id, @RequestBody GymUpdateDto gymUpdateDto){
        return new ResponseEntity<>(this.gymService.update(authorization, id, gymUpdateDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Add training type to a gym")
    @PostMapping("/{gymId}/add-training-type")
    public ResponseEntity<GymTrainingTypeDto> addTrainingTypeToGym(@RequestHeader("Authorization") String authorization,
                                                                @PathVariable Long gymId, @RequestBody NewGymTrainingTypeDto newGymTrainingTypeDto){
        return new ResponseEntity<>(this.gymService.addTrainingType(authorization, gymId, newGymTrainingTypeDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Remove training type from a gym")
    @PostMapping("/remove-training-type/{id}")
    public ResponseEntity<GymTrainingTypeDto> removeTrainingTypeToGym(@RequestHeader("Authorization") String authorization,
                                                                      @PathVariable Long id){
        return new ResponseEntity<>(this.gymService.removeTrainingType(authorization, id), HttpStatus.OK);
    }

    @ApiOperation(value = "All training type from a gym")
    @GetMapping("/{gymId}/training-types")
    public ResponseEntity<Page<GymTrainingTypeDto>> findAllTrainingTypes(@RequestHeader("Authorization") String authorization,
                                                                      @PathVariable Integer gymId, Pageable pageable){
        return new ResponseEntity<>(this.gymService.findAllTrainingTypes(pageable, authorization, gymId.longValue()), HttpStatus.OK);
    }
}
