package rs.raf.messageservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class MessageCreateDto {

    private String messageType;
    private Long userId;
    private String firstName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appointmentPlace;
    private String email;
    private LocalDateTime timeSent;
}
