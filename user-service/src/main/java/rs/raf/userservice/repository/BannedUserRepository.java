package rs.raf.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.userservice.domain.BannedUser;

@Repository
public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {
}
