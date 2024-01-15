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
    private String link;

    public MessageCreateDto(String messageType, Long userId, String firstName, LocalDate appointmentDate, LocalTime appointmentTime, String appointmentPlace, String email, LocalDateTime timeSent, String link) {
        this.messageType = messageType;
        this.userId = userId;
        this.firstName = firstName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentPlace = appointmentPlace;
        this.email = email;
        this.timeSent = timeSent;
        this.link = link;
    }

    public MessageCreateDto(){

    }
}
