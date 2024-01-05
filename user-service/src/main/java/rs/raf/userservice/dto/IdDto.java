package rs.raf.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdDto {

    private Long id;

    public IdDto(int id){
            this.id = (long) id;
    }

    public IdDto(){
    }
}

