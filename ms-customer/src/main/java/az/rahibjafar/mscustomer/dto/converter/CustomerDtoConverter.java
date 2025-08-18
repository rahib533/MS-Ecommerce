package az.rahibjafar.mscustomer.dto.converter;

import az.rahibjafar.mscustomer.dto.CustomerDto;
import az.rahibjafar.mscustomer.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {
    public CustomerDto convertToCustomerDto(Customer from) {
        return new CustomerDto(from.getId(),
                from.getFirstName(),
                from.getLastName(),
                from.getCif()
        );
    }
}
