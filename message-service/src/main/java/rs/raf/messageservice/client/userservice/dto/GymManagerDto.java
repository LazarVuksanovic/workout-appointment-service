package rs.raf.messageservice.client.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GymManagerDto extends UserDto {

    private String gymName;
    private LocalDate employmentDate;
}
