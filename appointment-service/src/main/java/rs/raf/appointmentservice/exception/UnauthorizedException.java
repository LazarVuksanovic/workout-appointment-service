package rs.raf.appointmentservice.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(String message) {
        super(message, ErrorCode.NOT_AVAILABLE, HttpStatus.UNAUTHORIZED);
    }}
