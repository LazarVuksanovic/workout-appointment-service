package rs.raf.messageservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import rs.raf.messageservice.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMessage(String emailReceiver, String subject, String messageToSend) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailReceiver);
        message.setSubject(subject);
        message.setText(messageToSend);

        this.javaMailSender.send(message);
    }
}
