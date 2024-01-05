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
        MessageType mt1 = new MessageType("RESET_PASSWORD", "Poruka za resetovanje sifre");
        MessageType mt2 = new MessageType("EMAIL_ACTIVATION", "Poruka za aktivaciju mejla");
        MessageType mt3 = new MessageType("SUCCESSFULLY_SCHEDULED", "Poruka za uspesno zakazan termin");
        MessageType mt4 = new MessageType("REMINDER_24H", "Poruka 24h pre treninga");
        MessageType mt5 = new MessageType("CANCELED_APPOINTMENT", "Poruka o otkazanom terminu");
        this.messageTypeRepository.saveAll(Arrays.asList(mt1, mt2, mt3, mt4, mt5));
    }
}
