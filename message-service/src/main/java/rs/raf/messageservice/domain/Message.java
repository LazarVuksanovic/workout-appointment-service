package rs.raf.messageservice.domain;

import lombok.Getter;
import lombok.Setter;
import rs.raf.messageservice.client.userservice.dto.UserDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private MessageType messageType;
    private Long userId;
    private String email;
    private LocalDateTime timeSent;
}
