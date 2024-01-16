package rs.raf.appointmentservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.appointmentservice.client.userservice.dto.IdDto;
import rs.raf.appointmentservice.dto.AppointmentDto;
import rs.raf.appointmentservice.dto.FilterDto;
import rs.raf.appointmentservice.dto.ScheduledAppointmentDto;
import rs.raf.appointmentservice.service.AppointmentService;
import rs.raf.appointmentservice.service.ScheduledAppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;
    private ScheduledAppointmentService scheduledAppointmentService;

    public AppointmentController(AppointmentService appointmentService, ScheduledAppointmentService scheduledAppointmentService){
        this.appointmentService = appointmentService;
        this.scheduledAppointmentService = scheduledAppointmentService;
    }

    @ApiOperation(value = "Find all appointments")
    @PostMapping()
    public ResponseEntity<Page<AppointmentDto>> findAll(Pageable pageable, @RequestBody FilterDto filterDto){
        System.out.println(filterDto.toString());
        return new ResponseEntity<>(this.appointmentService.findAll(pageable, filterDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Add appointment")
    @PostMapping("/add")
    public ResponseEntity<AppointmentDto> add(@RequestHeader("Authorization") String authorization,
                                                    @RequestBody AppointmentDto appointmentDto){
        return new ResponseEntity<>(this.appointmentService.add(authorization, appointmentDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Schedule an appointment")
    @PostMapping("/schedule")
    public ResponseEntity<ScheduledAppointmentDto> scheduleAppointment(@RequestHeader("Authorization") String authorization,
                                                                       @RequestBody IdDto appointmentId){
        return new ResponseEntity<>(this.scheduledAppointmentService.scheduleAppointment(authorization, appointmentId), HttpStatus.OK);
    }

    @ApiOperation(value = "Cancel an appointment")
    @PostMapping("/cancel")
    public ResponseEntity<ScheduledAppointmentDto> cancelAppointment(@RequestHeader("Authorization") String authorization,
                                                                       @RequestBody IdDto idDto){
        return new ResponseEntity<>(this.scheduledAppointmentService.cancelAppointment(authorization, idDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Find user appointments")
    @GetMapping("/user-appointments")
    public ResponseEntity<Page<AppointmentDto>> findUserAppointments(Pageable pageable,
                                                                     @RequestHeader("Authorization") String authorization){
        //FilterDto filterDto
        return new ResponseEntity<>(this.scheduledAppointmentService.findUserAppointments(pageable, authorization, null), HttpStatus.OK);
    }
}
