package az.rahibjafarov.apigateway.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;
import java.util.List;

@Service
public class ProxyGateway {
    private final WebClient client;

    public ProxyGateway(WebClient client) {
        this.client = client;
    }

    public <T> Mono<ResponseEntity<List<T>>> getList(String targetUri,
                                                     ServerHttpRequest incoming,
                                                     ParameterizedTypeReference<List<T>> typeRef) {
        return client.get()
                .uri(appendQuery(URI.create(targetUri), incoming))
                .headers(h -> propagateHeaders(incoming, h))
                .exchangeToMono(resp -> resp.toEntity(typeRef));
    }

    public <T> Mono<ResponseEntity<T>> get(String targetUri,
                                                     ServerHttpRequest incoming,
                                                     ParameterizedTypeReference<T> typeRef) {
        return client.get()
                .uri(appendQuery(URI.create(targetUri), incoming))
                .headers(h -> propagateHeaders(incoming, h))
                .exchangeToMono(resp -> resp.toEntity(typeRef));
    }

    public <T, R> Mono<ResponseEntity<R>> post(String targetUri,
                                               ServerHttpRequest incoming,
                                               T body,
                                               ParameterizedTypeReference<R> typeRef) {
        return client.post()
                .uri(appendQuery(URI.create(targetUri), incoming))
                .headers(h -> propagateHeaders(incoming, h))
                .bodyValue(body)
                .exchangeToMono(resp -> resp.toEntity(typeRef));
    }


    private void propagateHeaders(ServerHttpRequest incoming, HttpHeaders out) {
        String auth = incoming.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth != null && !auth.isBlank()) out.set(HttpHeaders.AUTHORIZATION, auth);

        incoming.getHeaders().forEach((k, v) -> {
            if (!HttpHeaders.HOST.equalsIgnoreCase(k)
                    && !HttpHeaders.CONTENT_LENGTH.equalsIgnoreCase(k)
                    && !out.containsKey(k)) {
                out.put(k, v);
            }
        });
    }

    private URI appendQuery(URI base, ServerHttpRequest incoming) {
        String q = incoming.getURI().getRawQuery();
        if (q == null || q.isBlank()) return base;
        String sep = (base.getRawQuery() == null) ? "?" : "&";
        return URI.create(base.toString() + sep + q);
    }
}
