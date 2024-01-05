package rs.raf.messageservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.dto.MessageDto;
import rs.raf.messageservice.mapper.MessageMapper;
import rs.raf.messageservice.repository.MessageRepository;
import rs.raf.messageservice.service.MessageService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    MessageRepository messageRepository;
    MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper){
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public Page<MessageDto> findAll(Pageable pageable) {
        return this.messageRepository.findAll(pageable).map(this.messageMapper::messageToMessageDto);
    }

    @Override
    public MessageDto addMessage(MessageCreateDto messageCreateDto) {
        return null;
    }
}
