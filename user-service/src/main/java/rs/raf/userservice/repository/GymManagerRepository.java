package rs.raf.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.userservice.domain.GymManager;

import java.util.Optional;

@Repository
public interface GymManagerRepository extends JpaRepository<GymManager, Long> {
    Optional<GymManager> findByGymName(String gymName);
}
