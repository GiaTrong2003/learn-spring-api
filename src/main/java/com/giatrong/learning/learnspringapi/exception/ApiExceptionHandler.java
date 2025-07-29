package com.giatrong.learning.learnspringapi.exception;

import com.giatrong.learning.learnspringapi.dto.response.ApiResponse;
import com.giatrong.learning.learnspringapi.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // this annotation indicates that this class will handle exceptions globally across the application
@Slf4j // using Lombok to generate a logger for this class
public class ApiExceptionHandler {

    /**
     * 1. Catch and handle: RuntimeException - unexpected errors
     */
    @ExceptionHandler(RuntimeException.class) // this annotation indicates that this method will handle RuntimeException
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
        // 1. log the exception
        log.error("RuntimeException orrurred: " + ex.getMessage());
        // 2.
        return ResponseEntity
                .status(500)
                .body(ApiResponse.error(500, "Internal Server Error: " + ex.getMessage())
                );
    }

    /**
     * 2. Catch and handle: General Exception Fallback
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralException(RuntimeException ex) {
        // 1. log the exception
        log.error("Unhandled exception: " + ex.getMessage());
        // 2. create an error code for uncategorized exceptions
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION; // using a predefined error code for uncategorized exceptions
        // 3. return a generic error response
        return ResponseEntity
                .status(errorCode.getStatusCode().value())
                .body(ApiResponse.error(errorCode.getStatusCode().value(), "Unhandled exception: " + ex.getMessage()));
    }

    /**
     * 3. Catch and handle: AppException (Custom Business Exception)
     */
    @ExceptionHandler(AppException.class) // this annotation indicates that this method will handle AppException
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode(); // get the error code from the exception
        // log the exception with the error code
        log.warn("AppException: " + errorCode.getMessage());
        // return a response with the error code and message
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.error(errorCode.getStatusCode().value(), errorCode.getMessage()));
    }


}
