package az.rahibjafar.msidentity.controller;

import az.rahibjafar.msidentity.dto.CreateUserRequest;
import az.rahibjafar.msidentity.dto.UserDto;
import az.rahibjafar.msidentity.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<UserDto> getAll() {
        return userService.findAll();
    }

    @GetMapping("get/{userName}")
    public UserDto get(@PathVariable String userName) {
        return userService.findByUsername(userName);
    }

    @PostMapping("/create")
    public UserDto create(@RequestBody CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest);
    }
}
