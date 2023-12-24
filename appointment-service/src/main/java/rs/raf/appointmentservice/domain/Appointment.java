package rs.raf.appointmentservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    private Integer maxPeople;

    private Integer availablePlaces;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

//    @ManyToOne
//    private GymTrainingType gymTrainingType;

    public Appointment(LocalDate date, LocalTime start, LocalTime end, Gym gym, TrainingType trainingType, Integer maxPeople, Integer availablePlaces){
        this.date = date;
        this.start = start;
        this.end = end;
        this.gym = gym;
        this.trainingType = trainingType;
        this.maxPeople = maxPeople;
        this.availablePlaces = availablePlaces;
    }

    public Appointment(){

    }
}
