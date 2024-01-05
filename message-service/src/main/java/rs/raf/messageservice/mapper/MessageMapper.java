package rs.raf.messageservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.messageservice.domain.Message;
import rs.raf.messageservice.dto.MessageDto;

@Component
public class MessageMapper {

    public MessageDto messageToMessageDto(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setMessageType(message.getMessageType());
        messageDto.setText(message.getText());
        messageDto.setUserId(message.getUserId());
        messageDto.setEmail(message.getEmail());
        messageDto.setTimeSent(message.getTimeSent());
        return messageDto;
    }
}
