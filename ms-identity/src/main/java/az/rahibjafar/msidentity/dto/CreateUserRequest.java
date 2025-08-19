package az.rahibjafar.msidentity.dto;

public record CreateUserRequest (
        String username,
        String password,
        String role
){
}