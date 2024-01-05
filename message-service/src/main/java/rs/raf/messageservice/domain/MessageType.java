package rs.raf.messageservice.domain;

import lombok.Getter;
import lombok.Setter;

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
}
