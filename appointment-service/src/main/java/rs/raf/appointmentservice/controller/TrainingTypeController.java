package rs.raf.appointmentservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.appointmentservice.dto.GymTrainingTypeDto;
import rs.raf.appointmentservice.dto.TrainingTypeDto;
import rs.raf.appointmentservice.service.TrainingTypeService;

@RestController
@RequestMapping("/training-type")
public class TrainingTypeController {

    private TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService){
        this.trainingTypeService = trainingTypeService;
    }

    @ApiOperation(value = "Get training types")
    @GetMapping("/")
    public ResponseEntity<Page<TrainingTypeDto>> findAll(Pageable pageable, @RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(this.trainingTypeService.findAll(pageable, authorization), HttpStatus.OK);
    }

    @ApiOperation(value = "Get training type")
    @GetMapping("/{id}")
    public ResponseEntity<TrainingTypeDto> trainingType(@RequestHeader("Authorization") String authorization,
                                                              @PathVariable Long id){
        return new ResponseEntity<>(this.trainingTypeService.trainingType(authorization, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get gym training type price")
    @GetMapping("/price/{gymId}/{typeId}")
    public ResponseEntity<GymTrainingTypeDto> price(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable Long gymId, @PathVariable Long typeId){
        return new ResponseEntity<>(this.trainingTypeService.price(authorization, gymId, typeId), HttpStatus.OK);
    }
}
