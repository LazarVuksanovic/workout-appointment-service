package rs.raf.messageservice.service;

import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.dto.MessageDto;

public interface MessageService {

    MessageDto addMessage(MessageCreateDto messageCreateDto);
}
