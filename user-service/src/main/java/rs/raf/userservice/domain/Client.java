package rs.raf.userservice.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Client extends User{

    private String membershipCardNumber;
    private Integer scheduledTrainings;
}
