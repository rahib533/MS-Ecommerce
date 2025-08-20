package az.rahibjafar.msorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Collection;
import java.util.Optional;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final DiscoveryClient discoveryClient;

    @Value("${app.security.audience:}")
    private String audience;

    public SecurityConfig(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/actuator/info", "/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        // Identity service-in URL-sini Eurekadan tap
        String jwkSetUri = discoveryClient.getInstances("ms-identity")
                .stream()
                .findFirst()
                .map(serviceInstance -> serviceInstance.getUri().toString() + "/.well-known/jwks.json")
                .orElseThrow(() -> new RuntimeException("identity-service not found from Eureka"));

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

        OAuth2TokenValidator<Jwt> defaults = JwtValidators.createDefault();
        OAuth2TokenValidator<Jwt> audienceValidator = jwt -> {
            if (audience == null || audience.isBlank()) return OAuth2TokenValidatorResult.success();
            Object aud = jwt.getClaims().get("aud");
            if (aud instanceof String s && s.equals(audience)) return OAuth2TokenValidatorResult.success();
            if (aud instanceof Collection<?> c && c.contains(audience)) return OAuth2TokenValidatorResult.success();
            return OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token", "Invalid audience", "")
            );
        };

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(defaults, audienceValidator));
        return decoder;
    }

    @Bean
    Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter scopes = new JwtGrantedAuthoritiesConverter();
        scopes.setAuthoritiesClaimName("scope");
        scopes.setAuthorityPrefix("SCOPE_");
        return jwt -> new JwtAuthenticationToken(jwt, scopes.convert(jwt),
                Optional.ofNullable(jwt.getClaimAsString("preferred_username"))
                        .orElseGet(() -> Optional.ofNullable(jwt.getClaimAsString("email"))
                                .orElse(jwt.getSubject())));
    }
}
