package rs.raf.messageservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rs.raf.messageservice.domain.MessageType;
import rs.raf.messageservice.repository.MessageTypeRepository;

import java.util.Arrays;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private MessageTypeRepository messageTypeRepository;

    public TestDataRunner(MessageTypeRepository messageTypeRepository){
        this.messageTypeRepository = messageTypeRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        MessageType mt1 = new MessageType("RESET_PASSWORD", "{user_name},\nŠifra je uspešno promenjena.");
        MessageType mt2 = new MessageType("EMAIL_ACTIVATION", "{user_name},\nLink za aktivaciju mejla.\nLink je aktivan 24h. \n{link}");
        MessageType mt3 = new MessageType("SUCCESSFULLY_SCHEDULED", "{user_name},\nUspesno zakazan termin {date} {time} {place}.");
        MessageType mt4 = new MessageType("REMINDER_24H", "{user_name},\nPodsecamo Vas na zakazan termin sutra {time} {place}.");
        MessageType mt5 = new MessageType("CANCELED_APPOINTMENT", "{user_name},\nTrening {date} {time} {place} je otkazan.");
        this.messageTypeRepository.saveAll(Arrays.asList(mt1, mt2, mt3, mt4, mt5));
    }
}
