package rs.raf.appointmentservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer numOfPersonalCoaches;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private Set<GymTrainingType> gymTrainingTypes = new HashSet<>();

    public Gym(String name, String description, Integer numOfPersonalCoaches){
        this.name = name;
        this.description = description;
        this.numOfPersonalCoaches = numOfPersonalCoaches;
    }

    public Gym(){
    }
}
