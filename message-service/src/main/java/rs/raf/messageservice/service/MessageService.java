package rs.raf.messageservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.dto.MessageDto;
import rs.raf.messageservice.dto.MessageTypeDto;
import rs.raf.messageservice.dto.MessageTypeUpdateDto;

public interface MessageService {

    Page<MessageDto> findAll(Pageable pageable);
    MessageCreateDto addMessage(MessageCreateDto messageCreateDto);
    Page<MessageTypeDto> findAllMessageTypes(String authorization, Pageable pageable);
    MessageTypeDto updateMessageType(String authorization, String messageType, MessageTypeUpdateDto messageTypeUpdateDto);
    MessageTypeDto deleteMessageType(String authorization, String messageType);
    MessageTypeDto addMessageType(String authorization, MessageTypeDto messageType);
}
