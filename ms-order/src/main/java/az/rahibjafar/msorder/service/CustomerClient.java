package az.rahibjafar.msorder.service;

import az.rahibjafar.msorder.dto.AccountDto;
import az.rahibjafar.msorder.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "ms-customer", path = "/v1/")
public interface CustomerClient {
    @GetMapping("/customer/getAll")
    List<CustomerDto> getAllCustomers();

    @GetMapping("/customer/get/{id}")
    CustomerDto getCustomerById(@PathVariable("id") UUID id);

    @GetMapping("/account/getAll")
    List<AccountDto> getAllAccount();

    @GetMapping("/account/get/{id}")
    AccountDto getAccountById(@PathVariable("id") UUID id);
}
