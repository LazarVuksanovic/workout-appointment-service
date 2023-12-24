package rs.raf.appointmentservice.exception;

import org.springframework.http.HttpStatus;

public class AppointmentNotAvailableException extends CustomException{

    public AppointmentNotAvailableException(String message) {
        super(message, ErrorCode.NOT_AVAILABLE, HttpStatus.NOT_ACCEPTABLE);
    }
}
