package az.rahibjafar.msproduct.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductNotInStock extends RuntimeException {
    public ProductNotInStock(String message) {
        super(message);
    }
}
