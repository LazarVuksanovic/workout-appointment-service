package rs.raf.appointmentservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.appointmentservice.domain.ScheduledAppointment;
import rs.raf.appointmentservice.dto.ScheduledAppointmentDto;

@Component
public class ScheduledAppointmentMapper {

    public ScheduledAppointmentDto scheduledAppointmentToScheduledAppointmentDto(ScheduledAppointment scheduledAppointment){
        ScheduledAppointmentDto scheduledAppointmentDto = new ScheduledAppointmentDto();
        scheduledAppointmentDto.setUserId(scheduledAppointment.getId().getUserId());
        scheduledAppointmentDto.setAppointmentId(scheduledAppointment.getId().getAppointmentId());
        return scheduledAppointmentDto;
    }

    public ScheduledAppointment scheduledAppointmentDtoToScheduledAppointment(ScheduledAppointmentDto scheduledAppointmentDto){
        ScheduledAppointment scheduledAppointment = new ScheduledAppointment();
        scheduledAppointment.getId().setUserId(scheduledAppointmentDto.getUserId());
        scheduledAppointment.getId().setAppointmentId(scheduledAppointmentDto.getAppointmentId());
        return scheduledAppointment;
    }
}
