package rs.raf.appointmentservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.domain.Gym;
import rs.raf.appointmentservice.domain.GymTrainingType;
import rs.raf.appointmentservice.domain.TrainingType;
import rs.raf.appointmentservice.repository.AppointmentRepository;
import rs.raf.appointmentservice.repository.GymRepository;
import rs.raf.appointmentservice.repository.GymTrainingTypeRepository;
import rs.raf.appointmentservice.repository.TrainingTypeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private GymRepository gymRepository;
    private AppointmentRepository appointmentRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private GymTrainingTypeRepository gymTrainingTypeRepository;

    public TestDataRunner(GymRepository gymRepository, AppointmentRepository appointmentRepository,
                          TrainingTypeRepository trainingTypeRepository, GymTrainingTypeRepository gymTrainingTypeRepository){
        this.gymRepository = gymRepository;
        this.appointmentRepository = appointmentRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.gymTrainingTypeRepository = gymTrainingTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //teretane
        Gym gym1 = new Gym("Blackworkout", "nova teretana kod brace jerkovica", 3);
        Gym gym2 = new Gym("Iron Republic", "velika dobro opremljena", 12);
        this.gymRepository.saveAll(Arrays.asList(gym1, gym2));
        //tipovi treninga
        TrainingType type1 = new TrainingType("powerlifting", true);
        TrainingType type2 = new TrainingType("pilates", false);
        TrainingType type3 = new TrainingType("kalistenika", true);
        TrainingType type4 = new TrainingType("joga", false);
        this.trainingTypeRepository.saveAll(Arrays.asList(type1, type2, type3, type4));
        //dostupni tipovi treninga u teretanama
        GymTrainingType gt11 = new GymTrainingType(gym1, type1, BigDecimal.valueOf(2500));
        GymTrainingType gt12 = new GymTrainingType(gym1, type2, BigDecimal.valueOf(800));
        GymTrainingType gt23 = new GymTrainingType(gym2, type3, BigDecimal.valueOf(2200));
        GymTrainingType gt24 = new GymTrainingType(gym2, type4, BigDecimal.valueOf(500));
        this.gymTrainingTypeRepository.saveAll(Arrays.asList(gt11, gt12, gt23, gt24));
        //Termini
        Appointment a1 = new Appointment(LocalDate.of(2023, 12, 22), LocalTime.of(19, 0), LocalTime.of(20, 0), gt11, 1, 1);
        Appointment a2 = new Appointment(LocalDate.of(2023, 12, 22), LocalTime.of(20, 0), LocalTime.of(21, 0), gt12, 12, 12);
        Appointment a3 = new Appointment(LocalDate.of(2023, 12, 22), LocalTime.of(18, 0), LocalTime.of(19, 0), gt23, 1, 1);
        Appointment a4 = new Appointment(LocalDate.of(2023, 12, 22), LocalTime.of(19, 0), LocalTime.of(20, 0), gt24, 12, 12);
        this.appointmentRepository.saveAll(Arrays.asList(a1, a2, a3, a4));
    }
}
