package az.rahibjafarov.apigateway.controller;

import az.rahibjafarov.apigateway.dto.AccountDto;
import az.rahibjafarov.apigateway.dto.CreateCustomerDto;
import az.rahibjafarov.apigateway.dto.CustomerDto;
import az.rahibjafarov.apigateway.service.ProxyGateway;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

@RestController
@RequestMapping("/api/gw")
public class CustomerProxyController {

    private final ProxyGateway proxy;

    public CustomerProxyController(ProxyGateway proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/accounts")
    public Mono<ResponseEntity<List<AccountDto>>> getAllAccounts(ServerHttpRequest incoming) {
        return proxy.getList(
                "lb://ms-customer/v1/account/getAll",
                incoming,
                new ParameterizedTypeReference<List<AccountDto>>() {}
        );
    }

    @GetMapping("/customers")
    public Mono<ResponseEntity<List<CustomerDto>>> getAllCustomers(ServerHttpRequest incoming) {
        return proxy.getList(
                "lb://ms-customer/v1/customer/getAll",
                incoming,
                new ParameterizedTypeReference<List<CustomerDto>>() {}
        );
    }

    @PostMapping("/customers")
    public Mono<ResponseEntity<CustomerDto>> createCustomer(ServerHttpRequest incoming,
                                                            @RequestBody CreateCustomerDto request) {
        return proxy.post(
                "lb://ms-customer/v1/customer/create",
                incoming,
                request,
                new ParameterizedTypeReference<CustomerDto>() {}
        );
    }

}