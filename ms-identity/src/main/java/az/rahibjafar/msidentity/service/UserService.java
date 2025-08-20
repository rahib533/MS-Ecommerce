package az.rahibjafar.msidentity.service;

import az.rahibjafar.msidentity.dto.CreateUserRequest;
import az.rahibjafar.msidentity.dto.UserDto;
import az.rahibjafar.msidentity.dto.converter.UserDtoConverter;
import az.rahibjafar.msidentity.model.Role;
import az.rahibjafar.msidentity.model.User;
import az.rahibjafar.msidentity.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;

    public UserService(UserRepository userRepository, UserDtoConverter userDtoConverter) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
    }

    public UserDto create(CreateUserRequest createUserRequest) {
        Role role = userRepository.findRoleByName(createUserRequest.role());

        User user = new User(createUserRequest.username(), createUserRequest.password(), Arrays.asList(role));
        return userDtoConverter.convertToUserDto(userRepository.createUser(user));
    }

    public UserDto findByUsername(String username) {
        return userDtoConverter.convertToUserDto(userRepository.findUserByUsername(username));
    }

    public UserDto findByUsernameAndPassword(String email, String password) {
        return userDtoConverter.convertToUserDto(userRepository.findUserByUsernameAndPassword(email, password));
    }

    public List<UserDto> findAll() {
        return userRepository.findAllUsers().stream().map(userDtoConverter::convertToUserDto).collect(Collectors.toList());
    }
}
