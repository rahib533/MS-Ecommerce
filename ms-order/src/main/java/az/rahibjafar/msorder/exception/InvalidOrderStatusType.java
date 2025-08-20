package az.rahibjafar.msorder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOrderStatusType extends RuntimeException {
    public InvalidOrderStatusType(String message) {
        super(message);
    }
}
