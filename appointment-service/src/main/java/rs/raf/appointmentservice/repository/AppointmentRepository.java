package rs.raf.appointmentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.domain.GymTrainingType;

import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Appointment a SET a.gymTrainingType = null WHERE a.gymTrainingType.id = :gymTrainingTypeId")
    void updateGymTrainingTypeToNull(Long gymTrainingTypeId);

    @Query("SELECT a FROM Appointment a WHERE a.gymTrainingType.gym.id = :gymId")
    Optional<Page<Appointment>> findAllByGymId(Pageable pageable, Long gymId);
}
