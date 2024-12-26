package UserProduct.userproductapplication.Controller;

import UserProduct.userproductapplication.JWT.JwtUtil;
import UserProduct.userproductapplication.Model.User;
import UserProduct.userproductapplication.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //http://localhost:8080/auth/register
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // Check if the user already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // Encrypt the password and save the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody User user) {
        // Validate user credentials
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            User foundUser = existingUser.get(); // Unwrap the Optional to get the User object
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                return jwtUtil.generateToken(foundUser.getUsername());
            }
        }
        throw new RuntimeException("Invalid username or password");
    }
}
