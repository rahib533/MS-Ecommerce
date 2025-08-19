package az.rahibjafar.msidentity.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(String keyId) {}