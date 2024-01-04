package rs.raf.messageservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;
    private HttpStatus httpStatus;

    public CustomException(String message, ErrorCode errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
