package com.model.guesthousebooking.Exception;

// Custom exceptions for the application
public class CustomExceptions {

    // Exception for email already exists
    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    // Exception for username already exists
    public static class UsernameAlreadyExistsException extends RuntimeException {
        public UsernameAlreadyExistsException(String message) {
            super(message);
        }
    }

    // Exception for user not approved
    public static class UserNotApprovedException extends RuntimeException {
        public UserNotApprovedException(String message) {
            super(message);
        }
    }

    // Exception for invalid credentials
    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception thrown when a user is not found in the database.
     */
    public static class UserNotFoundException extends RuntimeException {

        /**
         * Constructs a new UserNotFoundException with the specified detail message.
         *
         * @param message The detail message explaining the reason for the exception.
         */
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}