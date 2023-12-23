package rs.raf.appointmentservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.appointmentservice.dto.AppointmentDto;
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
    @GetMapping()
    public ResponseEntity<Page<AppointmentDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(this.appointmentService.findAll(pageable), HttpStatus.OK);
    }
    @ApiOperation(value = "Schedule an appointment")
    @PostMapping("/schedule")
    public ResponseEntity<ScheduledAppointmentDto> scheduleAppointment(@RequestBody ScheduledAppointmentDto scheduledAppointmentDto){
        return new ResponseEntity<>(this.scheduledAppointmentService.scheduleAppointment(scheduledAppointmentDto), HttpStatus.OK);
    }
}
