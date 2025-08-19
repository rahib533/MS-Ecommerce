package az.rahibjafar.msidentity.dto.converter;

import az.rahibjafar.msidentity.dto.UserDto;
import az.rahibjafar.msidentity.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDtoConverter {
    private final RoleDtoConverter roleDtoConverter;

    public UserDtoConverter(RoleDtoConverter roleDtoConverter) {
        this.roleDtoConverter = roleDtoConverter;
    }

    public UserDto convertToUserDto(User from) {

        return new UserDto(from.getUsername(),
                from.getPassword(),
                from.getRoles().stream().map(roleDtoConverter::convertToRoleDto).collect(Collectors.toList())
        );
    }
}
