package rs.raf.messageservice.service;

public interface EmailService {

    void sendSimpleMessage(String emailReceiver, String subject, String messageToSend);
}
