package rs.raf.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDto extends UserDto{

    private String membershipCardNumber;
    private int scheduledTrainings;
}
