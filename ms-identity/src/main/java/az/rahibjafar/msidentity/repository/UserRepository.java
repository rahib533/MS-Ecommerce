package az.rahibjafar.msidentity.repository;

import az.rahibjafar.msidentity.exception.RoleNotFoundException;
import az.rahibjafar.msidentity.exception.UserNotFoundException;
import az.rahibjafar.msidentity.model.Role;
import az.rahibjafar.msidentity.model.User;
import az.rahibjafar.msidentity.storage.UserDb;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    public User findUserByUsername(String username) {
        return UserDb.getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElseThrow(
                () -> new UserNotFoundException("User with username " + username + " not found")
        );
    }

    public User findUserById(UUID id) {
        return UserDb.getUsers().stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found")
        );
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        return UserDb.getUsers().stream().filter(
                    user -> user.getUsername().equals(username) && user.getPassword().equals(password)
                )
                .findFirst().orElseThrow(
                    () -> new UserNotFoundException("User with username " + username + " not found")
                );
    }

    public Role findRoleByName(String roleName) {
        return UserDb.getRoles().stream().filter(role -> role.getName().equals(roleName)).findFirst().orElseThrow(
                () -> new RoleNotFoundException("Role with name " + roleName + " not found")
        );
    }

    public User createUser(User user) {
        return UserDb.addUser(user);
    }

    public List<User> findAllUsers() {
        return UserDb.getUsers();
    }
}
