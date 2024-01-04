package rs.raf.messageservice.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.listener.helper.MessageHelper;
import rs.raf.messageservice.service.MessageService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class OrderListener {

    private MessageHelper messageHelper;
    private MessageService messageService;

    public OrderListener(MessageHelper messageHelper, MessageService messageService) {
        this.messageHelper = messageHelper;
        this.messageService = messageService;
    }

    @JmsListener(destination = "${destination.sendMessage}", concurrency = "5-10")
    public void addOrder(Message message) throws JMSException {
        MessageCreateDto messageCreateDto = this.messageHelper.getMessage(message, MessageCreateDto.class);
        this.messageService.addMessage(messageCreateDto);
    }
}


