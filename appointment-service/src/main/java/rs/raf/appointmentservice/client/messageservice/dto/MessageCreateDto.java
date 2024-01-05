package rs.raf.appointmentservice.client.messageservice.dto;

import lombok.Getter;
import lombok.Setter;

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
