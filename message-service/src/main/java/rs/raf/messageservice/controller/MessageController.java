package rs.raf.messageservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.dto.MessageDto;
import rs.raf.messageservice.listener.helper.MessageHelper;
import rs.raf.messageservice.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String messageDestination;

    public MessageController(MessageService messageService, JmsTemplate jmsTemplate, MessageHelper messageHelper,
                             @Value("${destination.sendMessage}") String messageDestination){
        this.messageService = messageService;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.messageDestination = messageDestination;
    }

    @ApiOperation(value = "Find all messages")
    @GetMapping
    public ResponseEntity<Page<MessageDto>> findAll(Pageable pageable){
        return new ResponseEntity<>(this.messageService.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Send messages")
    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageCreateDto messageCreateDto){
        this.jmsTemplate.convertAndSend(this.messageDestination, this.messageHelper.createTextMessage(messageCreateDto));
        return new ResponseEntity<>(this.messageService.addMessage(messageCreateDto), HttpStatus.OK);
    }
}
