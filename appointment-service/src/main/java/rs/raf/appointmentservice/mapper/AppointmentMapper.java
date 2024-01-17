package rs.raf.appointmentservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.dto.AppointmentDto;
import rs.raf.appointmentservice.repository.GymRepository;
import rs.raf.appointmentservice.repository.GymTrainingTypeRepository;
import rs.raf.appointmentservice.repository.TrainingTypeRepository;


@Component
public class AppointmentMapper {

    private GymTrainingTypeRepository gymTrainingTypeRepository;
    private GymRepository gymRepository;

    public AppointmentMapper(GymTrainingTypeRepository gymTrainingTypeRepository, GymRepository gymRepository){
        this.gymTrainingTypeRepository = gymTrainingTypeRepository;
        this.gymRepository = gymRepository;
    }

    public AppointmentDto appointmentToAppointmentDto(Appointment appointment){
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setStart(appointment.getStart());
        appointmentDto.setEnd(appointment.getEnd());
        appointmentDto.setMaxPeople(appointment.getMaxPeople());
        appointmentDto.setAvailablePlaces(appointment.getAvailablePlaces());
        appointmentDto.setGymId(appointment.getGymTrainingType().getGym().getId());
        appointmentDto.setTrainingTypeId(appointment.getGymTrainingType().getTrainingType().getId());
        appointmentDto.setGymName(appointment.getGymTrainingType().getGym().getName());
        appointmentDto.setPrice(appointment.getGymTrainingType().getPrice());
        appointmentDto.setTrainingTypeName(appointment.getGymTrainingType().getTrainingType().getName());
        return appointmentDto;
    }

    public Appointment appointmentDtoToAppointment(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setDate(appointmentDto.getDate());
        appointment.setStart(appointmentDto.getStart());
        appointment.setEnd(appointmentDto.getEnd());
        appointment.setMaxPeople(appointmentDto.getMaxPeople());
        appointment.setAvailablePlaces(appointmentDto.getAvailablePlaces());
        appointment.setGymTrainingType(this.gymTrainingTypeRepository.findById(appointmentDto.getGymTrainingTypeId()).get());
        return appointment;
    }
}
