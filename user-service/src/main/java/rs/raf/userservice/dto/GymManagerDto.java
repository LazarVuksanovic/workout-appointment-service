package rs.raf.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GymManagerDto extends UserDto {

    private Long id;
    private String gymName;
    private LocalDate employmentDate;
    private String email;
}
