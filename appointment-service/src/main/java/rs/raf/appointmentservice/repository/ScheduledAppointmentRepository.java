package rs.raf.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.appointmentservice.domain.ScheduledAppointment;
import rs.raf.appointmentservice.domain.ScheduledAppointmentId;

@Repository
public interface ScheduledAppointmentRepository extends JpaRepository<ScheduledAppointment, ScheduledAppointmentId> {
}
