package rs.raf.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.appointmentservice.domain.GymTrainingType;

@Repository
public interface GymTrainingTypeRepository extends JpaRepository<GymTrainingType, Long> {
}
