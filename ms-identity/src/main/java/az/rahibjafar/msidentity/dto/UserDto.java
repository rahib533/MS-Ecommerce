package az.rahibjafar.msidentity.dto;

import java.util.List;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private List<RoleDto> roles;

    public UserDto(String username, String password, List<RoleDto> roles) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    public UUID getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<RoleDto> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
