package az.rahibjafar.msproduct.event.model;

import java.util.UUID;

public record OrderCancelledEvent(
        UUID orderId,
        String message
) {
}
