package rs.raf.appointmentservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "gym_training_type")
public class GymTrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    private BigDecimal price;

    public GymTrainingType(Gym gym, TrainingType trainingType, BigDecimal price){
        this.gym = gym;
        this.trainingType = trainingType;
        this.price = price;
    }

    public GymTrainingType(){
    }

    @Override
    public String toString() {
        return "GymTrainingType{" +
                "id=" + id +
                ", gym=" + gym +
                ", trainingType=" + trainingType +
                ", price=" + price +
                '}';
    }
}
