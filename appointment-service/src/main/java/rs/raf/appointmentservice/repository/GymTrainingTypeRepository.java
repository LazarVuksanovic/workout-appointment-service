package rs.raf.appointmentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.raf.appointmentservice.domain.Gym;
import rs.raf.appointmentservice.domain.GymTrainingType;
import rs.raf.appointmentservice.domain.ScheduledAppointment;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymTrainingTypeRepository extends JpaRepository<GymTrainingType, Long> {

    Optional<GymTrainingType> findByGymIdAndTrainingTypeId(Long gymId, Long trainingTypeId);
    @Query("SELECT gt FROM GymTrainingType gt WHERE gt.gym = :gym")
    Optional<Page<GymTrainingType>> findByGymId(Pageable pageable, Gym gym);
}
