package rs.raf.userservice.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GymManager extends User{

    private String gymName;
    private String employmentDate;
}
