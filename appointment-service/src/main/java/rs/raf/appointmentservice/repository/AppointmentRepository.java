package rs.raf.appointmentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.dto.AppointmentDto;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

//    Page<Appointment> findAllByTrainingTypeIdAndGymTrainingTypeIsIndividualAndDateDayOfWeek(
//            Long trainingTypeId, boolean isIndividual, DayOfWeek dayOfWeek, Pageable pageable);
//    Page<Appointment> findAllByTrainingTypeIdAndGymTrainingTypeIsIndividual(Long trainingTypeId, boolean isIndividual);
//    Page<Appointment> findAllByTrainingTypeIdAndDateDayOfWeek(Long trainingTypeId, DayOfWeek dayOfWeek, Pageable pageable);
//    Page<Appointment> findAllByGymTrainingTypeIsIndividualAndDateDayOfWeek(boolean isIndividual, DayOfWeek dayOfWeek, Pageable pageable);
//    Page<Appointment> findAllByDateDayOfWeek(
//            DayOfWeek dayOfWeek, Pageable pageable);
//    Page<Appointment> findAllByIsIndividual(boolean isIndividual, Pageable pageable);
//    Page<Appointment> findAllByTrainingTypeId(Long trainingTypeId, Pageable pageable);
}
