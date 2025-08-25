package az.rahibjafar.mspayment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPaymentStatusType extends RuntimeException {
    public InvalidPaymentStatusType(String message) {
        super(message);
    }
}