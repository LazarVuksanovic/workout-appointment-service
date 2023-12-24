package rs.raf.userservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.userservice.dto.ClientCreateDto;
import rs.raf.userservice.dto.ClientDto;
import rs.raf.userservice.dto.UserUpdateDto;
import rs.raf.userservice.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService){ this.clientService = clientService; }

    @ApiOperation(value = "Find all clients")
    @GetMapping
    public ResponseEntity<Page<ClientDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(this.clientService.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Register client")
    @PostMapping
    public ResponseEntity<ClientDto> saveClient(@RequestBody @Valid ClientCreateDto clientCreateDto) {
        return new ResponseEntity<>(this.clientService.add(clientCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "User schedule appointment")
    @GetMapping("/schedule-appointment")
    public ResponseEntity<Long> scheduleAppointment(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(this.clientService.scheduleAppointment(authorization), HttpStatus.OK);
    }

    @ApiOperation(value = "User cancel appointment")
    @GetMapping("/cancel-appointment")
    public ResponseEntity<Long> cancelAppointment(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(this.clientService.cancelAppointment(authorization), HttpStatus.OK);
    }
}
