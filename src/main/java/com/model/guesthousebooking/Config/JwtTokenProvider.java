package com.model.guesthousebooking.Config;

import com.model.guesthousebooking.Entities.Role;
import com.model.guesthousebooking.Entities.User;
import com.model.guesthousebooking.Exception.InvalidJwtTokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    // Generate JWT Token
    public String generateToken(User user) {
        try {
            logger.debug("Generating token for user: {}", user.getUsername());

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + validityInMilliseconds);

            String token = Jwts.builder()
                    .setSubject(user.getUsername()) // Use username instead of the whole User object
                    .claim("roles", user.getRoles().stream().map(Role::getRoleName).toList()) // Use role names only
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(SIGNATURE_ALGORITHM, secretKey)
                    .compact();

            logger.debug("Token generated successfully for user: {}", user.getUsername());
            return token;
        } catch (Exception e) {
            logger.error("Error generating JWT token for user: {}", user.getUsername(), e);
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    // Validate Token and Get Claims
    public Claims validateTokenAndGetClaims(String token) {
        try {
            logger.debug("Validating token...");
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            logger.debug("Token validated successfully: claims={}", claims);
            return claims;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token has expired");
            throw new InvalidJwtTokenException("JWT token has expired. Please log in again.", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token");
            throw new InvalidJwtTokenException("JWT token is unsupported.", e);
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT token");
            throw new InvalidJwtTokenException("JWT token is malformed.", e);
        } catch (SignatureException e) {
            logger.error("JWT signature validation failed");
            throw new InvalidJwtTokenException("JWT signature validation failed.", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT token is null or empty");
            throw new InvalidJwtTokenException("JWT token is null or empty.", e);
        }
    }

    // Get Username from Token
    public String getUsernameFromToken(String token) {
        logger.debug("Extracting username from token...");
        return validateTokenAndGetClaims(token).getSubject();
    }

    // Get Role(s) from Token
    public List<String> getRolesFromToken(String token) {
        try {
            logger.debug("Extracting roles from token...");
            List<?> rawRoles = validateTokenAndGetClaims(token).get("roles", List.class);
            List<String> roles = rawRoles.stream().map(Object::toString).toList(); // Convert to List<String>
            logger.debug("Roles extracted: {}", roles);
            return roles;
        } catch (Exception e) {
            logger.error("Failed to extract roles from token", e);
            throw new InvalidJwtTokenException("Failed to extract roles from token.", e);
        }
    }
}