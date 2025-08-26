package az.rahibjafar.msorder.event.model;

import java.util.UUID;

public record StockRollbackEvent(
        UUID orderId,
        UUID productId,
        Integer count,
        String message
) {
}
