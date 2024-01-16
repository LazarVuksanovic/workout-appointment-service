package rs.raf.messageservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.raf.messageservice.client.userservice.dto.UserDto;
import rs.raf.messageservice.domain.Message;
import rs.raf.messageservice.domain.MessageType;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.dto.MessageDto;
import rs.raf.messageservice.dto.MessageTypeDto;
import rs.raf.messageservice.dto.MessageTypeUpdateDto;
import rs.raf.messageservice.exception.NotFoundException;
import rs.raf.messageservice.mapper.MessageMapper;
import rs.raf.messageservice.mapper.MessageTypeMapper;
import rs.raf.messageservice.repository.MessageRepository;
import rs.raf.messageservice.repository.MessageTypeRepository;
import rs.raf.messageservice.service.MessageService;

import java.util.Optional;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private MessageMapper messageMapper;
    private MessageTypeRepository messageTypeRepository;
    private MessageTypeMapper messageTypeMapper;
    private RestTemplate userServiceRestTemplate;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper,
                              MessageTypeRepository messageTypeRepository, MessageTypeMapper messageTypeMapper,
                              RestTemplate userServiceRestTemplate){
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.messageTypeRepository = messageTypeRepository;
        this.messageTypeMapper = messageTypeMapper;
        this.userServiceRestTemplate = userServiceRestTemplate;
    }

    @Override
    public Page<MessageDto> findAll(Pageable pageable) {
        return this.messageRepository.findAll(pageable).map(this.messageMapper::messageToMessageDto);
    }

    @Override
    public MessageCreateDto addMessage(MessageCreateDto messageCreateDto) {
        Message message = this.messageMapper.mesageCreateDtoToMessage(messageCreateDto);
        MessageType messageType = this.messageTypeRepository.findById(messageCreateDto.getMessageType()).get();
        message.setText(messageType.mapValuesToText(messageCreateDto));
        this.messageRepository.save(message);
        return messageCreateDto;
    }

    @Override
    public Page<MessageTypeDto> findAllMessageTypes(String authorization, Pageable pageable) {
        //provaravamo da li je admin
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/only-admin", HttpMethod.GET, request, UserDto.class);
        }catch (HttpClientErrorException e) {
            if (!e.getStatusCode().equals(HttpStatus.OK))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        return this.messageTypeRepository.findAll(pageable).map(messageTypeMapper::messageTypeToMessageTypeDto);
    }

    @Override
    public MessageTypeDto updateMessageType(String authorization, String messageType, MessageTypeUpdateDto messageTypeUpdateDto) {
        //provaravamo da li je admin
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/only-admin", HttpMethod.GET, request, UserDto.class);
        }catch (HttpClientErrorException e) {
            if (!e.getStatusCode().equals(HttpStatus.OK))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }
        System.out.println("OVOVOVVO\n" + messageType);
        Optional<MessageType> messageType1 = this.messageTypeRepository.findById(messageType);
        messageType1.get().setText(messageTypeUpdateDto.getText());
        return this.messageTypeMapper.messageTypeToMessageTypeDto(messageType1.get());
    }

    @Override
    public MessageTypeDto deleteMessageType(String authorization, String messageType) {
        //provaravamo da li je admin
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/only-admin", HttpMethod.GET, request, UserDto.class);
        }catch (HttpClientErrorException e) {
            if (!e.getStatusCode().equals(HttpStatus.OK))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        Optional<MessageType> messageType1 = this.messageTypeRepository.findById(messageType);
        this.messageTypeRepository.delete(messageType1.get());
        return this.messageTypeMapper.messageTypeToMessageTypeDto(messageType1.get());
    }

    @Override
    public MessageTypeDto addMessageType(String authorization, MessageTypeDto messageTypeDto) {
        //provaravamo da li je admin
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);

            HttpEntity<String> request = new HttpEntity<>(headers);
            this.userServiceRestTemplate.exchange("/user/only-admin", HttpMethod.GET, request, UserDto.class);
        }catch (HttpClientErrorException e) {
            if (!e.getStatusCode().equals(HttpStatus.OK))
                throw new NotFoundException("NEVALIDAN KORISNIK");
        }

        this.messageTypeRepository.save(this.messageTypeMapper.messageTypeDtoToMessageType(messageTypeDto));
        return messageTypeDto;
    }
}
