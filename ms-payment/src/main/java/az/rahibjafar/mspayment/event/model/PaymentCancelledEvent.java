package az.rahibjafar.mspayment.event.model;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCancelledEvent(
        UUID paymentId,
        UUID orderId,
        String fromAccountNumber,
        String toAccountNumber,
        BigDecimal amount,
        String message
) {
}
