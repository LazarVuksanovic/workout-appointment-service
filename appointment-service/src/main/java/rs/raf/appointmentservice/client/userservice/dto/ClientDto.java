package rs.raf.appointmentservice.client.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto extends UserDto{

    private String membershipCardNumber;
    private Integer scheduledTrainings;
}
