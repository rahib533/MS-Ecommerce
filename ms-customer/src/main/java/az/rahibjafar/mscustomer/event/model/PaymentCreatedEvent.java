package az.rahibjafar.mscustomer.event.model;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreatedEvent(
        UUID paymentId,
        UUID orderId,
        String fromAccountNumber,
        String toAccountNumber,
        BigDecimal amount
) {
}
