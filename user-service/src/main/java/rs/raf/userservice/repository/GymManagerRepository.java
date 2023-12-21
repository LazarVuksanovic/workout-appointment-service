package rs.raf.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.userservice.domain.GymManager;

@Repository
public interface GymManagerRepository extends JpaRepository<GymManager, Long> {
}
