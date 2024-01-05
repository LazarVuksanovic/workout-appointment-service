package rs.raf.messageservice.dto;

import lombok.Getter;
import lombok.Setter;
import rs.raf.messageservice.domain.MessageType;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageCreateDto {

    private String text;
    private MessageType messageType;
    private Long userId;
    private String email;
    private LocalDateTime timeSent;
}
