package rs.raf.messageservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.messageservice.domain.MessageType;
import rs.raf.messageservice.dto.MessageTypeDto;

@Component
public class MessageTypeMapper {

    public MessageTypeDto messageTypeToMessageTypeDto(MessageType messageType){
        MessageTypeDto messageTypeDto = new MessageTypeDto();
        messageTypeDto.setMessageType(messageType.getMessageType());
        messageTypeDto.setText(messageType.getText());
        return messageTypeDto;
    }

    public MessageType messageTypeDtoToMessageType(MessageTypeDto messageTypeDto){
        MessageType messageType = new MessageType();
        messageType.setText(messageTypeDto.getText());
        messageType.setMessageType(messageTypeDto.getMessageType());
        return messageType;
    }
}
