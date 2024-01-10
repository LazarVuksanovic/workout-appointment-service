package rs.raf.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {

    private String oldPassword;
    private String newPassword;
}
