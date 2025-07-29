package com.giatrong.learning.learnspringapi.exception;

import com.giatrong.learning.learnspringapi.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice // this annotation indicates that this class will handle exceptions globally across the application
public class ApiExceptionHandler {

    /**
     * Catch and handle exceptions for resources not found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    // this annotation indicates that this method will handle ResourceNotFoundException
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<?> response = ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        // ResponseEntity is a wrapper for the response body and status code
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Catch another exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(Exception ex, WebRequest request) {
        ApiResponse<?> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        // In ra log để dev có thể debug
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
