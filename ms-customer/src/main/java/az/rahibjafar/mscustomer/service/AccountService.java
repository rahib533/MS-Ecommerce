package az.rahibjafar.mscustomer.service;

import az.rahibjafar.mscustomer.dto.AccountDto;
import az.rahibjafar.mscustomer.dto.CreateAccountRequest;
import az.rahibjafar.mscustomer.dto.converter.AccountDtoConverter;
import az.rahibjafar.mscustomer.event.model.PaymentCancelledEvent;
import az.rahibjafar.mscustomer.event.model.PaymentCompletedEvent;
import az.rahibjafar.mscustomer.event.model.PaymentCreatedEvent;
import az.rahibjafar.mscustomer.event.producer.PaymentEventProducer;
import az.rahibjafar.mscustomer.exception.AccountNotFoundException;
import az.rahibjafar.mscustomer.exception.InsufficientAmountException;
import az.rahibjafar.mscustomer.model.Account;
import az.rahibjafar.mscustomer.model.Customer;
import az.rahibjafar.mscustomer.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountDtoConverter accountDtoConverter;
    private final CustomerServis customerServis;
    private final PaymentEventProducer paymentEventProducer;


    public AccountService(
            AccountRepository accountRepository,
            AccountDtoConverter accountDtoConverter,
            CustomerServis customerServis,
            PaymentEventProducer paymentEventProducer
    ) {
        this.accountRepository = accountRepository;
        this.accountDtoConverter = accountDtoConverter;
        this.customerServis = customerServis;
        this.paymentEventProducer = paymentEventProducer;
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

    protected Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new AccountNotFoundException("Account with number " + accountNumber + " not found")
        );
    }

    @Transactional
    public void Transfer(final PaymentCreatedEvent paymentCreatedEvent){
        Account from = findByAccountNumber(paymentCreatedEvent.fromAccountNumber());
        Account to = findByAccountNumber(paymentCreatedEvent.toAccountNumber());

        if(from.getBalance().compareTo(paymentCreatedEvent.amount()) < 0){
            paymentEventProducer.publishPaymentCancelled(
                    new PaymentCancelledEvent(
                            paymentCreatedEvent.paymentId(),
                            paymentCreatedEvent.orderId(),
                            paymentCreatedEvent.fromAccountNumber(),
                            paymentCreatedEvent.toAccountNumber(),
                            paymentCreatedEvent.amount(),
                            "Transfer failed: Insufficient amount to transfer: " + paymentCreatedEvent.amount()
                    )
            );
            throw new InsufficientAmountException("Insufficient amount to transfer");
        }

        from.setBalance(from.getBalance().subtract(paymentCreatedEvent.amount()));
        to.setBalance(to.getBalance().add(paymentCreatedEvent.amount()));

        accountRepository.save(from);
        accountRepository.save(to);

        paymentEventProducer.publishPaymentCompleted(
                new PaymentCompletedEvent(
                        paymentCreatedEvent.paymentId(),
                        paymentCreatedEvent.orderId(),
                        paymentCreatedEvent.fromAccountNumber(),
                        paymentCreatedEvent.toAccountNumber(),
                        paymentCreatedEvent.amount()
                )
        );
    }
}
