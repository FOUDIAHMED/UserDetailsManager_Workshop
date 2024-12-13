package ahmed.foudi.Userdetailsmanager.controller;

import ahmed.foudi.Userdetailsmanager.dto.UserCreateRequest;
import ahmed.foudi.Userdetailsmanager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/anyone")
    public ResponseEntity<?> getTestAPI(){
        return ResponseEntity.ok("Anyone can access me");
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
    @GetMapping("/manager")
    public ResponseEntity<?> getTestAPI2(Principal principal){
        return ResponseEntity.ok(principal.getName()+": has logged in.");
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request) {

            userService.createUser(request);
            return ResponseEntity.ok("User created successfully");

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        try {
            userService.deleteUser(username);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
