package rs.raf.messageservice.domain;

import lombok.Getter;
import lombok.Setter;
import rs.raf.messageservice.dto.MessageCreateDto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class MessageType {
    @Id
    private String messageType;
    private String text;

    public MessageType(String messageType){
        this.messageType = messageType;
    }

    public MessageType(String messageType, String text){
        this.messageType = messageType;
        this.text = text;
    }

    public MessageType(){
    }

    public String mapValuesToText(MessageCreateDto message){
        String finalText = this.text;
        finalText = finalText.replace("{user_name}", message.getFirstName());
        finalText = finalText.replace("{date}", message.getAppointmentDate().toString());
        finalText = finalText.replace("{time}", message.getAppointmentTime().toString());
        finalText = finalText.replace("{place}", message.getAppointmentPlace());
        return finalText;
    }
}
