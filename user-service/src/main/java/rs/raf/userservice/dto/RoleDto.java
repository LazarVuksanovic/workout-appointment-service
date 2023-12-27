package rs.raf.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

    private Long id;
    private String role;

    public RoleDto(int id, String role){
        this.id = (long) id;
        this.role = role;
    }

    public RoleDto(){

    }
}
