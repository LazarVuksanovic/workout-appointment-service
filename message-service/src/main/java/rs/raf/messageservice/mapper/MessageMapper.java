package rs.raf.messageservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.messageservice.domain.Message;
import rs.raf.messageservice.dto.MessageCreateDto;
import rs.raf.messageservice.dto.MessageDto;
import rs.raf.messageservice.repository.MessageTypeRepository;

@Component
public class MessageMapper {

    private MessageTypeRepository messageTypeRepository;

    public MessageMapper(MessageTypeRepository messageTypeRepository){
        this.messageTypeRepository = messageTypeRepository;
    }

    public MessageDto messageToMessageDto(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setMessageType(message.getMessageType());
        messageDto.setUserId(message.getUserId());
        messageDto.setEmail(message.getEmail());
        messageDto.setTimeSent(message.getTimeSent());
        return messageDto;
    }

    public Message mesageCreateDtoToMessage(MessageCreateDto messageCreateDto){
        Message message = new Message();
        message.setMessageType(this.messageTypeRepository.findById(messageCreateDto.getMessageType()).get());
        message.setUserId(messageCreateDto.getUserId());
        message.setEmail(messageCreateDto.getEmail());
        message.setTimeSent(messageCreateDto.getTimeSent());
        return message;
    }
}
