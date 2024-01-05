package rs.raf.messageservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.messageservice.dto.MessageDto;
import rs.raf.messageservice.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @ApiOperation(value = "Find all messages")
    @GetMapping
    public ResponseEntity<Page<MessageDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(this.messageService.findAll(pageable), HttpStatus.OK);
    }
}
