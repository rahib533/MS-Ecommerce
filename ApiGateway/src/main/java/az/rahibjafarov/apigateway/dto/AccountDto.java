package az.rahibjafarov.apigateway.dto;

import java.util.UUID;

public record AccountDto(
        UUID id,
        String accountNumber,
        double balance,
        CustomerDto customer
) {}