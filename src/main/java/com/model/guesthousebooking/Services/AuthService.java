package com.model.guesthousebooking.Services;

import com.model.guesthousebooking.Config.JwtTokenProvider;
import com.model.guesthousebooking.Security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String authenticate(String username, String password) {
        try {
            logger.info("Authenticating user: {}", username);

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            logger.debug("Authentication successful for username: {}", username);

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Authentication set in SecurityContext for username: {}", username);

            // Ensure principal is of type CustomUserDetails
            if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
                logger.error("Authentication principal is not an instance of CustomUserDetails");
                throw new RuntimeException("Unexpected authentication principal type");
            }

            // Extract user details
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            logger.debug("User details: username={}, roles={}", userDetails.getUsername(), userDetails.getAuthorities());

            // Generate JWT token
            String token = jwtTokenProvider.generateToken(userDetails.getUser());
            logger.info("JWT token generated for username: {}", username);

            return token;
        } catch (BadCredentialsException ex) {
            logger.error("Invalid credentials for user: {}", username, ex);
            throw new BadCredentialsException("Invalid username or password", ex);
        } catch (Exception ex) {
            logger.error("Unexpected error during authentication for user: {}", username, ex);
            throw new RuntimeException("An unexpected error occurred during authentication", ex);
        }
    }
}