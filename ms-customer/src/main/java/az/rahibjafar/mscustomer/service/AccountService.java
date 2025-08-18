package az.rahibjafar.mscustomer.service;

import az.rahibjafar.mscustomer.dto.AccountDto;
import az.rahibjafar.mscustomer.dto.CreateAccountRequest;
import az.rahibjafar.mscustomer.dto.converter.AccountDtoConverter;
import az.rahibjafar.mscustomer.exception.AccountNotFoundException;
import az.rahibjafar.mscustomer.model.Account;
import az.rahibjafar.mscustomer.model.Customer;
import az.rahibjafar.mscustomer.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountDtoConverter accountDtoConverter;
    private final CustomerServis customerServis;


    public AccountService(AccountRepository accountRepository, AccountDtoConverter accountDtoConverter, CustomerServis customerServis) {
        this.accountRepository = accountRepository;
        this.accountDtoConverter = accountDtoConverter;
        this.customerServis = customerServis;
    }

    public AccountDto create(CreateAccountRequest createAccountRequest) {
        Customer customer = customerServis.findById(createAccountRequest.getCustomerId());

        Account account = new Account(createAccountRequest.getAccountNumber(),
                createAccountRequest.getBalance(),
                customer
        );

        return accountDtoConverter.convertToAccountDto(accountRepository.save(account));
    }

    public AccountDto getById(UUID id) {
        return accountDtoConverter.convertToAccountDto(accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException("Account with id " + id + " not found")
        ));
    }

    protected Account findById(UUID id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException("Account with id " + id + " not found")
        );
    }

    public List<AccountDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountDtoConverter::convertToAccountDto)
                .collect(Collectors.toList());
    }
}
