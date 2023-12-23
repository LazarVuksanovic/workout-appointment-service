package rs.raf.appointmentservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.appointmentservice.dto.GymDto;
import rs.raf.appointmentservice.dto.GymUpdateDto;
import rs.raf.appointmentservice.service.GymService;

@RestController
@RequestMapping("/gym")
public class GymController {

    private GymService gymService;

    public GymController(GymService gymService){
        this.gymService = gymService;
    }

    @ApiOperation(value = "Edit gym")
    @PostMapping("/{id}/edit")
    public ResponseEntity<GymDto> updateGym(@RequestHeader("Authorization") String authorization,
                                            @RequestParam Long id, @RequestBody GymUpdateDto gymUpdateDto){
        return new ResponseEntity<>(this.gymService.update(id, gymUpdateDto), HttpStatus.OK);
    }
}
