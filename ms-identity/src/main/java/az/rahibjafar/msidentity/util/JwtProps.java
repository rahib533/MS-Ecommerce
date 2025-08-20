package az.rahibjafar.msidentity.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@ConfigurationProperties(prefix = "auth.jwt")
public record JwtProps(
        String issuer,
        long expirySeconds,
        String scope,
        List<String> audience
) {}
