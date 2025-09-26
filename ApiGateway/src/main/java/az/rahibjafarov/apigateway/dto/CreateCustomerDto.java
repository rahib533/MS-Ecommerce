package az.rahibjafarov.apigateway.dto;

public record CreateCustomerDto(
        String firstName,
        String lastName,
        String cif
) {}

