package rs.raf.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.appointmentservice.domain.GymTrainingType;

import java.util.Optional;

@Repository
public interface GymTrainingTypeRepository extends JpaRepository<GymTrainingType, Long> {

    Optional<GymTrainingType> findByGymIdAndTrainingTypeId(Long gymId, Long trainingTypeId);
}
