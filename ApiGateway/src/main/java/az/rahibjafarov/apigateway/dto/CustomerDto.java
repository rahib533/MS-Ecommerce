package az.rahibjafarov.apigateway.dto;

import java.util.UUID;

public record CustomerDto(
        UUID id,
        String firstName,
        String lastName,
        String cif
) {}
