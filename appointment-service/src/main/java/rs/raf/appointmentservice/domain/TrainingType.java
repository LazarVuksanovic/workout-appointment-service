package rs.raf.appointmentservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL)
    private Set<GymTrainingType> gymTrainingTypes = new HashSet<>();

    private boolean isIndividual;
    public TrainingType(String name, boolean isIndividual){
        this.name = name;
        this.isIndividual = isIndividual;
    }

    public TrainingType(){
    }

    @Override
    public String toString() {
        return "TrainingType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gymTrainingTypes=" + gymTrainingTypes +
                ", isIndividual=" + isIndividual +
                '}';
    }
}
