package com.giatrong.learning.learnspringapi.exception;

import com.giatrong.learning.learnspringapi.dto.response.ApiResponse;
import com.giatrong.learning.learnspringapi.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 4. Catch and handle: validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        // Tạo một map để lưu trữ tất cả các lỗi validation
        Map<String, String> errors = new HashMap<>();

        // Lặp qua tất cả các lỗi và thêm vào map
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Log chi tiết các lỗi validation
        log.warn("Validation failed: {}", errors);

        ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;

        // Tạo response với details về các field bị lỗi
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .message(errorCode.getMessage())
                        .data(errors) // Trả về chi tiết các lỗi validation
                        .build());
    }

    /**
     * 5. Catch and handle: AccessDeniedException (Spring Security)
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;

        // Log thông tin access denied
        log.warn("Access denied: {}", ex.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.error(
                        errorCode.getStatusCode().value(),
                        errorCode.getMessage()
                ));
    }

}
