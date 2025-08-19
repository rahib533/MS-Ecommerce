package az.rahibjafar.msidentity.controller;

import az.rahibjafar.msidentity.dto.LoginRequest;
import az.rahibjafar.msidentity.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) throws Exception {
        return authService.getToken(loginRequest.username(), loginRequest.password());
    }
}