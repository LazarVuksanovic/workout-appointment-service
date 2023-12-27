package rs.raf.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.appointmentservice.domain.Appointment;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
