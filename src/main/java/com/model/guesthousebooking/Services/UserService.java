package com.model.guesthousebooking.Services;

import com.model.guesthousebooking.Config.JwtTokenProvider;
import com.model.guesthousebooking.Entities.User;
import com.model.guesthousebooking.Exception.CustomExceptions;
import com.model.guesthousebooking.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_APPROVED = "APPROVED";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Registers a new user with the given details.
     *
     * @param firstName User's first name.
     * @param lastName  User's last name.
     * @param username  User's username.
     * @param email     User's email.
     * @param password  User's password.
     * @param role      User's role.
     */
    public void registerUser(String firstName, String lastName, String username, String email, String password, String role) {
        validateEmailAndUsername(email, username);

        // Create and save the new user
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Hash password
        user.setRole(role);
        user.setStatus(STATUS_PENDING); // Default status

        userRepository.save(user);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the User, if found.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Approves a user by updating their status to "APPROVED".
     *
     * @param userId The ID of the user to approve.
     */
    public void approveUser(Long userId) {
        User user = findUserById(userId);
        user.setStatus(STATUS_APPROVED);
        userRepository.save(user);
    }

    /**
     * Authenticates a user by validating their username and password,
     * checking their approval status, and generating a JWT token.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return A JWT token if login is successful.
     * @throws CustomExceptions.InvalidCredentialsException if username or password is invalid.
     * @throws CustomExceptions.UserNotApprovedException if the user's account is not approved.
     */
    public String loginUser(String username, String password) {
        User user = findUserByUsernameOrThrow(username);
        validatePassword(password, user.getPassword());
        checkIfUserApproved(user.getStatus());
        return jwtTokenProvider.generateToken(user);
    }

    /**
     * Helper method to validate email and username during registration.
     *
     * @param email    The email to validate.
     * @param username The username to validate.
     * @throws CustomExceptions.EmailAlreadyExistsException if the email is already registered.
     * @throws CustomExceptions.UsernameAlreadyExistsException if the username is already taken.
     */
    private void validateEmailAndUsername(String email, String username) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomExceptions.EmailAlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByUsername(username)) {
            throw new CustomExceptions.UsernameAlreadyExistsException("Username already exists");
        }
    }

    /**
     * Helper method to find a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The User if found.
     * @throws CustomExceptions.UserNotFoundException if the user is not found.
     */
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found with ID: " + userId));
    }

    /**
     * Helper method to find a user by username or throw an exception if not found.
     *
     * @param username The username to search for.
     * @return The User entity if found.
     * @throws CustomExceptions.InvalidCredentialsException if the user is not found.
     */
    private User findUserByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomExceptions.InvalidCredentialsException("Invalid username or password"));
    }

    /**
     * Helper method to validate the user's password.
     *
     * @param rawPassword    The raw password provided by the user.
     * @param encodedPassword The encoded password stored in the database.
     * @throws CustomExceptions.InvalidCredentialsException if the password is invalid.
     */
    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomExceptions.InvalidCredentialsException("Invalid username or password");
        }
    }

    /**
     * Helper method to check if the user's account is approved.
     *
     * @param status The status of the user.
     * @throws CustomExceptions.UserNotApprovedException if the user's account is not approved.
     */
    private void checkIfUserApproved(String status) {
        if (!STATUS_APPROVED.equals(status)) {
            throw new CustomExceptions.UserNotApprovedException("User is not approved yet");
        }
    }
}