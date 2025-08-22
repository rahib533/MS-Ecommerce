package az.rahibjafar.msorder.event.model;

import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        UUID productId,
        UUID customerId,
        String accountNumber,
        Integer count
)
{ }
