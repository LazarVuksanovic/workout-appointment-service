package rs.raf.messageservice.dto;

import lombok.Getter;
import lombok.Setter;
import rs.raf.messageservice.client.userservice.dto.UserDto;
import rs.raf.messageservice.domain.MessageType;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDto {

    private Long id;
    private String text;
    private MessageType messageType;
    private Long userId;
    private String email;
    private LocalDateTime timeSent;
}
