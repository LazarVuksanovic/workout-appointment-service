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
import rs.raf.messageservice.dto.MessageTypeDto;
import rs.raf.messageservice.dto.MessageTypeUpdateDto;
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

    @ApiOperation(value = "Find all messages")
    @GetMapping("/user/{id}")
    public ResponseEntity<Page<MessageDto>> findAllUserMessages(Pageable pageable,@PathVariable Long id){
        return new ResponseEntity<>(this.messageService.findAllUserMessages(pageable, id), HttpStatus.OK);
    }

    @ApiOperation(value = "Send messages")
    @PostMapping
    public ResponseEntity<MessageCreateDto> sendMessage(@RequestBody MessageCreateDto messageCreateDto){
        this.jmsTemplate.convertAndSend(this.messageDestination, this.messageHelper.createTextMessage(messageCreateDto));
        return new ResponseEntity<>(this.messageService.addMessage(messageCreateDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Find all message types")
    @GetMapping("/type")
    public ResponseEntity<Page<MessageTypeDto>> findAllMessageTypes(@RequestHeader("Authorization") String authorization,
                                                                    Pageable pageable){
        return new ResponseEntity<>(this.messageService.findAllMessageTypes(authorization, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Get message type")
    @GetMapping("/type/{messageType}")
    public ResponseEntity<MessageTypeDto> findMessageType(@RequestHeader("Authorization") String authorization,
                                                                    @PathVariable String messageType){
        return new ResponseEntity<>(this.messageService.findMessageType(authorization, messageType), HttpStatus.OK);
    }

    @ApiOperation(value = "Edit message type")
    @PostMapping("/type/{messageType}")
    public ResponseEntity<MessageTypeDto> updateMessageType(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable String messageType,
                                                            @RequestBody MessageTypeUpdateDto messageTypeUpdateDto){
        return new ResponseEntity<>(this.messageService.updateMessageType(authorization, messageType, messageTypeUpdateDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete message type")
    @PostMapping("/type/{messageType}/delete")
    public ResponseEntity<MessageTypeDto> deleteMessageType(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable String messageType){
        return new ResponseEntity<>(this.messageService.deleteMessageType(authorization, messageType), HttpStatus.OK);
    }

    @ApiOperation(value = "Add message type")
    @PostMapping("/type")
    public ResponseEntity<MessageTypeDto> addMessageType(@RequestHeader("Authorization") String authorization,
                                                            @RequestBody MessageTypeDto messageTypeDto){
        return new ResponseEntity<>(this.messageService.addMessageType(authorization, messageTypeDto), HttpStatus.OK);
    }
}
