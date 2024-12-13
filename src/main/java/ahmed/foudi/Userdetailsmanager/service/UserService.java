package ahmed.foudi.Userdetailsmanager.service;


import ahmed.foudi.Userdetailsmanager.dto.UserCreateRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;



@Service
public class UserService {
    

    private final JdbcUserDetailsManager userDetailsManager;
    public UserService(JdbcUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;

    }

    public void createUser(UserCreateRequest userCreateRequest) {
        UserDetails user = User
            .withUsername(userCreateRequest.getUsername())
            .password(userCreateRequest.getPassword())
            .roles(userCreateRequest.getRoles())
            .build();

        if (userDetailsManager.userExists(userCreateRequest.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        userDetailsManager.createUser(user);
    }

    public void deleteUser(String username) {
        if (!userDetailsManager.userExists(username)) {
            throw new RuntimeException("User not found");
        }
        userDetailsManager.deleteUser(username);
    }

    public boolean userExists(String username) {
        return userDetailsManager.userExists(username);
    }
} 