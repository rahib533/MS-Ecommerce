package az.rahibjafar.mscustomer.controller;

import az.rahibjafar.mscustomer.dto.AccountDto;
import az.rahibjafar.mscustomer.dto.CreateAccountRequest;
import az.rahibjafar.mscustomer.service.AccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/getAll")
    public List<AccountDto> getAll() {
        return accountService.findAll();
    }

    @GetMapping("get/{id}")
    public AccountDto get(@PathVariable UUID id) {
        return accountService.getById(id);
    }

    @PostMapping("/create")
    public AccountDto create(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.create(createAccountRequest);
    }
}
