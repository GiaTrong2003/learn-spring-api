package com.giatrong.learning.learnspringapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to handle resource not found scenarios.
 * This exception can be thrown when a requested resource (like a user) is not found in the database.
 * It will return a 404 Not Found HTTP status code.
 * icon: thunderbolt ⚡️ -> indicates ( chỉ ra ) an error or exception -> spring bean
 */
@ResponseStatus // mark this exception to be handled by a global exception handler
    public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message); // call the constructor of the superclass (RuntimeException)
    }
}
