package az.rahibjafar.mspayment.dto.converter;

import az.rahibjafar.mspayment.dto.PaymentDto;
import az.rahibjafar.mspayment.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentDtoConverter {
    public PaymentDto convertToOrderDto(Payment from) {
        return new PaymentDto(
                from.getId(),
                from.getOrderId(),
                from.getFromAccountNumber(),
                from.getToAccountNumber(),
                from.getTotalAmount(),
                from.getCreatedDate(),
                from.getStatusCompletedDate(),
                from.getStatusFailedDate(),
                from.getStatus()
        );
    }
}
