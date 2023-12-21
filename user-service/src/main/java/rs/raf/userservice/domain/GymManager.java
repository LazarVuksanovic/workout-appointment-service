package rs.raf.userservice.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class GymManager extends User{

    private String gymName;
    private LocalDate employmentDate;
}
