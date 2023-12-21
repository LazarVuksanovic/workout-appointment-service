package rs.raf.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.userservice.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
