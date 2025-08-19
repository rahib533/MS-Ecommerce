package az.rahibjafar.msidentity.dto.converter;

import az.rahibjafar.msidentity.dto.RoleDto;
import az.rahibjafar.msidentity.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter {
    public RoleDto convertToRoleDto(Role from) {
        return new RoleDto(from.getName());
    }
}
