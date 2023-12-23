package rs.raf.appointmentservice.exception;

import org.springframework.http.HttpStatus;

public class BannedUserException extends CustomException{

    public BannedUserException(String message) {
        super(message, ErrorCode.ALREADY_BANNED_USER, HttpStatus.I_AM_A_TEAPOT);
    }

}
