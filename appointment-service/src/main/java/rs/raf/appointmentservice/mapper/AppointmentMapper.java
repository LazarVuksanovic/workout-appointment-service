package rs.raf.appointmentservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.appointmentservice.domain.Appointment;
import rs.raf.appointmentservice.dto.AppointmentDto;


@Component
public class AppointmentMapper {

    public AppointmentDto appointmentToAppointmentDto(Appointment appointment){
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setStart(appointment.getStart());
        appointmentDto.setEnd(appointment.getEnd());
        appointmentDto.setMaxPeople(appointment.getMaxPeople());
        appointmentDto.setAvailablePlaces(appointment.getAvailablePlaces());
        appointmentDto.setGymId(appointment.getGym().getId());
        appointmentDto.setTrainingTypeId(appointment.getTrainingType().getId());
        return appointmentDto;
    }
}
