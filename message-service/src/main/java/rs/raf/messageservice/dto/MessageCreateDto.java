package rs.raf.messageservice.dto;

import lombok.Getter;
import lombok.Setter;
import rs.raf.messageservice.client.userservice.dto.UserDto;
import rs.raf.messageservice.domain.MessageType;

@Getter
@Setter
public class MessageCreateDto {

    private String text;
    private MessageType messageType;
    private UserDto user;
}
