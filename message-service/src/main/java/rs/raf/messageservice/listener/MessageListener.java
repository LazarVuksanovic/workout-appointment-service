package rs.raf.messageservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import rs.raf.messageservice.domain.MessageType;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.listener.helper.MessageHelper;
import rs.raf.messageservice.mapper.MessageMapper;
import rs.raf.messageservice.repository.MessageTypeRepository;
import rs.raf.messageservice.service.EmailService;
import rs.raf.messageservice.service.MessageService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class MessageListener {

    private MessageHelper messageHelper;
    private MessageService messageService;
    private MessageMapper messageMapper;
    private MessageTypeRepository messageTypeRepository;
    private EmailService emailService;

    public MessageListener(MessageHelper messageHelper, MessageService messageService, MessageMapper messageMapper,
                           MessageTypeRepository messageTypeRepository, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.messageTypeRepository = messageTypeRepository;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.sendMessage}", concurrency = "5-10")
    public void addMessage(Message message) throws JMSException {
        MessageCreateDto messageCreateDto = this.messageHelper.getMessage(message, MessageCreateDto.class);
        MessageType messageType = this.messageTypeRepository.findById(messageCreateDto.getMessageType()).get();
        String messageToSend = messageType.mapValuesToText(messageCreateDto);
        this.emailService.sendSimpleMessage("lvuksanovic1021rn@raf.rs", messageType.getMessageType(), messageToSend);
    }
}


