package az.rahibjafar.msidentity.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String password;
    private List<Role> roles;

    public User(String username, String password, List<Role> roles) {
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
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
