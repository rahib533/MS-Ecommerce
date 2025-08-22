package az.rahibjafar.msorder.event.model;

import java.math.BigDecimal;
import java.util.UUID;

public record StockReservedEvent(
        UUID orderId,
        UUID productId,
        UUID customerId,
        String accountNumber,
        Integer count,
        BigDecimal totalAmount
) {
}
