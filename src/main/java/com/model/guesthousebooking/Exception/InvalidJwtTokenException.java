package com.model.guesthousebooking.Exception;

/**
 * Custom exception for handling invalid JWT tokens.
 */
public class InvalidJwtTokenException extends RuntimeException {

    // Constructor with a custom message
    public InvalidJwtTokenException(String message) {
        super(message);
    }

    // Constructor with a custom message and a throwable cause
    public InvalidJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}