package rs.raf.messageservice.dto;

import lombok.Getter;
import lombok.Setter;
import rs.raf.messageservice.domain.MessageType;

@Getter
@Setter
public class MessageDto {

    private Long id;
    private String text;
    private MessageType messageType;
}
