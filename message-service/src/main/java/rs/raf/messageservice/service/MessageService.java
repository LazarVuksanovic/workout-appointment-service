package rs.raf.messageservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.dto.MessageDto;

public interface MessageService {

    Page<MessageDto> findAll(Pageable pageable);
    MessageDto addMessage(MessageCreateDto messageCreateDto);
}
