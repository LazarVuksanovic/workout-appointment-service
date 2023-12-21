package rs.raf.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class GymManagerCreateDto {

    @NotNull
    private String username;

    @Length(min = 8, max = 20)
    private String password;

    @Email
    private String email;

    private LocalDate dateOfBirth;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String gymName;
}
