package rs.raf.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.domain.GymTrainingType;

import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Appointment a SET a.gymTrainingType = null WHERE a.gymTrainingType.id = :gymTrainingTypeId")
    void updateGymTrainingTypeToNull(Long gymTrainingTypeId);
}
