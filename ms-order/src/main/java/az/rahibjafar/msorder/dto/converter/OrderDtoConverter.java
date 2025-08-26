package az.rahibjafar.msorder.dto.converter;

import az.rahibjafar.msorder.dto.OrderDto;
import az.rahibjafar.msorder.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoConverter {
    public OrderDto convertToOrderDto(Order from) {
        return new OrderDto(from.getId(),
                from.getProductId(),
                from.getCustomerID(),
                from.getAccountNumber(),
                from.getCount(),
                from.getTotalAmount(),
                from.getCreatedDate(),
                from.getConfirmedDate(),
                from.getReservedDate(),
                from.getCancelledDate(),
                from.getStatus()
        );
    }
}
