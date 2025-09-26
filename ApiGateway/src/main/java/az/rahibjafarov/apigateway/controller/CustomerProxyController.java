package az.rahibjafarov.apigateway.controller;

import az.rahibjafarov.apigateway.service.ProxyGateway;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gw")
public class CustomerProxyController {

    private final ProxyGateway proxy;

    public CustomerProxyController(ProxyGateway proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/accounts")
    public Mono<ResponseEntity<String>> getAllAccounts(ServerHttpRequest incoming) {
        return proxy.get("lb://ms-customer/v1/account/getAll", incoming);
    }

    @GetMapping("/customers")
    public Mono<ResponseEntity<String>> getAllCustomers(ServerHttpRequest incoming) {
        return proxy.get("lb://ms-customer/v1/customer/getAll", incoming);
    }
}