package rs.raf.appointmentservice.exception;

import org.springframework.http.HttpStatus;

public class AlreadyScheduledException extends CustomException{

    public AlreadyScheduledException(String message) {
        super(message, ErrorCode.NOT_AVAILABLE, HttpStatus.ALREADY_REPORTED);
    }
}
