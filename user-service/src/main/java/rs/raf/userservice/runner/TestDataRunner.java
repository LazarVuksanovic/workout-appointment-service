package rs.raf.userservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rs.raf.userservice.domain.Admin;
import rs.raf.userservice.domain.Client;
import rs.raf.userservice.repository.AdminRepository;
import rs.raf.userservice.repository.UserRepository;

import java.time.LocalDate;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private UserRepository userRepository;

    public TestDataRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //unosimo admina
        Admin admin = new Admin();
        admin.setEmail("lvuksanovic1021rn@raf.rs");
        admin.setPassword("admin");
        admin.setUsername("admin");
        admin.setRole("admin");
        //unosimo korisnika
        Client client = new Client();
        client.setEmail("lvuksanovic1021rn@raf.rs");
        client.setPassword("123");
        client.setUsername("kila");
        client.setRole("client");
        client.setFirstName("Lazar");
        client.setLastName("Vuksanovic");
        client.setDateOfBirth(LocalDate.of(2002, 4, 3));
        client.setScheduledTrainings(0);
        client.setMembershipCardNumber("LV03040244");
        this.userRepository.save(admin);
        this.userRepository.save(client);
    }
}
