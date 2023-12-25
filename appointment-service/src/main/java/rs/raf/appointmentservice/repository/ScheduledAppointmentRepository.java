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
//    @Query("SELECT s FROM ScheduledAppointment s WHERE s.id.appointmentId = :appointmentId")
//    Page<ScheduledAppointment> findAllByAppointmentId(@Param("appointmentId") Long appointmentId, Pageable pageable);
}
