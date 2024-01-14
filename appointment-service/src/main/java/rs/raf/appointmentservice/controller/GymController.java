package rs.raf.appointmentservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
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

    @ApiOperation(value = "Get gym")
    @GetMapping("/{id}")
    public ResponseEntity<GymDto> updateGym(@RequestHeader("Authorization") String authorization,
                                            @PathVariable Long id){
        return new ResponseEntity<>(this.gymService.getGym(authorization, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Edit gym")
    @PostMapping("/{id}/edit")
    public ResponseEntity<GymDto> updateGym(@RequestHeader("Authorization") String authorization,
                                            @PathVariable Long id, @RequestBody GymUpdateDto gymUpdateDto){
        return new ResponseEntity<>(this.gymService.update("Bearer " + authorization, id, gymUpdateDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Add training type to a gym")
    @PostMapping("/{gymId}/add-training-type")
    public ResponseEntity<GymTrainingTypeDto> addTrainingTypeToGym(@RequestHeader("Authorization") String authorization,
                                                                @PathVariable Long gymId, @RequestBody NewGymTrainingTypeDto newGymTrainingTypeDto){
        return new ResponseEntity<>(this.gymService.addTrainingType("Bearer " + authorization, gymId, newGymTrainingTypeDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Remove training type from a gym")
    @PostMapping("/{gymId}/remove-training-type/{trainingTypeId}")
    public ResponseEntity<GymTrainingTypeDto> removeTrainingTypeToGym(@RequestHeader("Authorization") String authorization,
                                                                      @PathVariable Long gymId, @PathVariable Long trainingTypeId){
        return new ResponseEntity<>(this.gymService.removeTrainingType("Bearer " + authorization, gymId, trainingTypeId), HttpStatus.OK);
    }
}
