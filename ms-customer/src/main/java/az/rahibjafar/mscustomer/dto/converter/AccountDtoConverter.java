package az.rahibjafar.mscustomer.dto.converter;

import az.rahibjafar.mscustomer.dto.AccountDto;
import az.rahibjafar.mscustomer.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConverter {
    public AccountDto convertToAccountDto(Account from) {
        return new AccountDto(from.getId(),
                from.getAccountNumber(),
                from.getBalance(),
                from.getCustomer()
        );
    }
}
