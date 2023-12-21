package rs.raf.userservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rs.raf.userservice.domain.Admin;
import rs.raf.userservice.repository.AdminRepository;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private AdminRepository adminRepository;

    public TestDataRunner(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //unosimo admina
        Admin admin = new Admin();
        admin.setEmail("lvuksanovic1021rn@raf.rs");
        admin.setPassword("admin");
        admin.setUsername("admin");
        admin.setRole("admin");
        this.adminRepository.save(admin);
    }
}
