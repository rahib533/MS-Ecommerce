package az.rahibjafar.msorder.event.model;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompletedEvent(
    UUID paymentId,
    UUID orderId,
    String fromAccountNumber,
    String toAccountNumber,
    BigDecimal amount)
{ }
