package rs.raf.appointmentservice.service;

import rs.raf.appointmentservice.dto.GymDto;
import rs.raf.appointmentservice.dto.GymUpdateDto;

public interface GymService {

    GymDto update(Long id, GymUpdateDto gymUpdateDto);
}
