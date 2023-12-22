package rs.raf.userservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.userservice.domain.BannedUser;
import rs.raf.userservice.dto.BannedUserDto;

@Component
public class BannedUserMapper {

    public BannedUser bannedUserDtoToBannedUser(BannedUserDto bannedUserDto){
        BannedUser bannedUser = new BannedUser();
        bannedUser.setId(bannedUserDto.getId());
        return bannedUser;
    }
}
