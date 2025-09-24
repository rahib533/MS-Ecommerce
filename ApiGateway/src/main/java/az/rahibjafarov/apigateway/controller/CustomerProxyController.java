package az.rahibjafarov.apigateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/gw")
public class CustomerProxyController {

    private final WebClient.Builder webClientBuilder;

    public CustomerProxyController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/accounts")
    public Mono<ResponseEntity<String>> getAllAccounts(ServerHttpRequest incoming) {
        String auth = incoming.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        return webClientBuilder.build()
                .get()
                .uri("lb://ms-customer/v1/account/getAll")
                .headers(h -> {
                    if (auth != null && !auth.isBlank()) {
                        h.set(HttpHeaders.AUTHORIZATION, auth);
                    }
                })
                .exchangeToMono(resp ->
                        resp.toEntity(String.class)
                );
    }

    @GetMapping("/customers")
    public Mono<ResponseEntity<String>> getAllCustomers(ServerHttpRequest incoming) {
        String auth = incoming.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        return webClientBuilder.build()
                .get()
                .uri("lb://ms-customer/v1/customer/getAll")
                .headers(h -> {
                    if (auth != null && !auth.isBlank()) {
                        h.set(HttpHeaders.AUTHORIZATION, auth);
                    }
                })
                .exchangeToMono(resp ->
                        resp.toEntity(String.class)
                );
    }
}