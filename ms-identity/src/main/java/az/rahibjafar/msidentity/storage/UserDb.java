package az.rahibjafar.msidentity.storage;

import az.rahibjafar.msidentity.model.AuthKey;
import az.rahibjafar.msidentity.model.Role;
import az.rahibjafar.msidentity.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDb {
    private static List<Role> roles = Arrays.asList(new Role("Admin"), new Role("User"));
    private static List<User> users = new ArrayList<>();
    private static List<AuthKey> authKeys = new ArrayList<>();

    public static void loadSeeds(){
        users.add(new User("rahibjafar", "123", roles));
    }

    public static List<User> getUsers() {
        return users;
    }

    public static User addUser(User user) {
        users.add(user);
        return user;
    }
    public static List<Role> getRoles() {
        return roles;
    }
    public static List<AuthKey> getAuthKeys() {
        return authKeys;
    }
    public static AuthKey addAuthKey(AuthKey authKey) {
        authKeys.add(authKey);
        return authKey;
    }
}
