package com.model.guesthousebooking.Controllers;

import com.model.guesthousebooking.Dto.LoginRequest;
import com.model.guesthousebooking.Dto.LoginResponse;
import com.model.guesthousebooking.Dto.RegisterRequest;
import com.model.guesthousebooking.Exception.CustomExceptions;
import com.model.guesthousebooking.Services.AuthService;
import com.model.guesthousebooking.Services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Constructor Injection
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            userService.registerUser(
                    registerRequest.getFirstName(),
                    registerRequest.getLastName(),
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    "USER"
            );
            return ResponseEntity.ok("User registered successfully!");
        } catch (RuntimeException ex) {
            if (ex.getMessage().equals("Email already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Login request received for username: {}", loginRequest.getUsername());
            logger.debug("Login payload: {}", loginRequest);

            // Validate input
            if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
                logger.error("Invalid login request: {}", loginRequest);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password are required");
            }

            // Delegate authentication to AuthService
            String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            logger.info("Authentication successful for username: {}", loginRequest.getUsername());

            // Return the JWT token
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException ex) {
            logger.error("Invalid credentials for username: {}", loginRequest.getUsername(), ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (CustomExceptions.UserNotApprovedException ex) {
            logger.error("User not approved for username: {}", loginRequest.getUsername(), ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        } catch (StackOverflowError ex) {
            // Log stack overflow error for deeper inspection
            logger.error("StackOverflowError occurred during login for username: {}", loginRequest.getUsername(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error occurred. Please contact support.");
        } catch (Exception ex) {
            logger.error("Unexpected error during login for username: {}", loginRequest.getUsername(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}