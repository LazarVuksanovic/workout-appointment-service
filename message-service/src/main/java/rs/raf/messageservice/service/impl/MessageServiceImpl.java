package rs.raf.messageservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.dto.MessageDto;
import rs.raf.messageservice.service.MessageService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Override
    public MessageDto addMessage(MessageCreateDto messageCreateDto) {
        return null;
    }
}