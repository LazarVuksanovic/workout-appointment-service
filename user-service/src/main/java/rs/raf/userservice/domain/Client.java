package rs.raf.userservice.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Client extends User{

    private String membershipCardNumber;
    private int scheduledTrainings;
}
