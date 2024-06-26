package rs.raf.appointmentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.raf.appointmentservice.domain.ScheduledAppointment;
import rs.raf.appointmentservice.domain.ScheduledAppointmentId;

import java.util.List;

@Repository
public interface ScheduledAppointmentRepository extends JpaRepository<ScheduledAppointment, ScheduledAppointmentId> {

    @Query("SELECT sa FROM ScheduledAppointment sa WHERE sa.id.appointmentId = :appointmentId")
    List<ScheduledAppointment> findAllByAppointmentId(Long appointmentId);
    @Query("SELECT sa FROM ScheduledAppointment sa WHERE sa.id.userId = :userId")
    List<ScheduledAppointment> findUserAppointments(Long userId);

}
