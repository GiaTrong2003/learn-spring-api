package com.giatrong.learning.learnspringapi.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * how to declare an enum:
 * public enum EnumName {
 * CONSTANT_NAME1(value1, value2),
 * CONSTANT_NAME2(value1, value2),
 * ...
 * }
 */
@Getter
public enum ErrorCode {

    // ========== SYSTEM ERRORS (9xxx) ==========
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR(9001, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(9002, "Service temporarily unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    DATABASE_ERROR(9003, "Database connection error", HttpStatus.INTERNAL_SERVER_ERROR),
    
    // ========== VALIDATION ERRORS (1xxx) ==========
    VALIDATION_FAILED(1000, "Validation failed", HttpStatus.BAD_REQUEST),
    INVALID_INPUT(1001, "Invalid input data", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD(1002, "Required field is missing", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT(1003, "Invalid data format", HttpStatus.BAD_REQUEST),
    INVALID_JSON(1004, "Invalid JSON format", HttpStatus.BAD_REQUEST),
    
    // ========== AUTHENTICATION ERRORS (2xxx) ==========
    UNAUTHENTICATED(2000, "Authentication required", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS(2001, "Invalid username or password", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(2002, "Token has expired", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(2003, "Invalid or malformed token", HttpStatus.UNAUTHORIZED),
    TOKEN_MISSING(2004, "Authorization token is missing", HttpStatus.UNAUTHORIZED),
    
    // ========== AUTHORIZATION ERRORS (3xxx) ==========
    ACCESS_DENIED(3000, "Access denied", HttpStatus.FORBIDDEN),
    INSUFFICIENT_PRIVILEGES(3001, "Insufficient privileges", HttpStatus.FORBIDDEN),
    RESOURCE_FORBIDDEN(3002, "Access to this resource is forbidden", HttpStatus.FORBIDDEN),
    
    // ========== USER ERRORS (4xxx) ==========
    USER_NOT_FOUND(4000, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(4001, "User already exists", HttpStatus.CONFLICT),
    USERNAME_ALREADY_TAKEN(4002, "Username is already taken", HttpStatus.CONFLICT),
    EMAIL_ALREADY_TAKEN(4003, "Email is already taken", HttpStatus.CONFLICT),
    USER_DISABLED(4004, "User account is disabled", HttpStatus.FORBIDDEN),
    USER_LOCKED(4005, "User account is locked", HttpStatus.FORBIDDEN),
    
    // ========== VALIDATION SPECIFIC ERRORS (5xxx) ==========
    USERNAME_INVALID(5000, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(5001, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(5002, "Invalid email format", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(5003, "Invalid phone number format", HttpStatus.BAD_REQUEST),
    DOB_INVALID(5004, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_WEAK(5005, "Password does not meet security requirements", HttpStatus.BAD_REQUEST),
    
    // ========== RESOURCE ERRORS (6xxx) ==========
    RESOURCE_NOT_FOUND(6000, "Resource not found", HttpStatus.NOT_FOUND),
    RESOURCE_ALREADY_EXISTS(6001, "Resource already exists", HttpStatus.CONFLICT),
    RESOURCE_CONFLICT(6002, "Resource conflict", HttpStatus.CONFLICT),
    
    // ========== REQUEST ERRORS (7xxx) ==========
    BAD_REQUEST(7000, "Bad request", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER(7001, "Invalid parameter", HttpStatus.BAD_REQUEST),
    MISSING_PARAMETER(7002, "Missing required parameter", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED(7003, "HTTP method not allowed", HttpStatus.METHOD_NOT_ALLOWED),
    UNSUPPORTED_MEDIA_TYPE(7004, "Unsupported media type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    
    // ========== RATE LIMITING & QUOTA ERRORS (8xxx) ==========
    RATE_LIMIT_EXCEEDED(8000, "Rate limit exceeded", HttpStatus.TOO_MANY_REQUESTS),
    QUOTA_EXCEEDED(8001, "Quota exceeded", HttpStatus.TOO_MANY_REQUESTS),
    
    // ========== LEGACY CODES (for backward compatibility) ==========
    INVALID_KEY(1010, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(4001, "User existed", HttpStatus.CONFLICT), // Maps to USER_ALREADY_EXISTS
    USER_NOT_EXISTED(4000, "User is not existed", HttpStatus.NOT_FOUND), // Maps to USER_NOT_FOUND
    UNAUTHORIZED(3000, "You do not have permission", HttpStatus.FORBIDDEN); // Maps to ACCESS_DENIED


    // ==  Constructor ==
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    // == Fields ==
    private int code;
    private String message;
    private HttpStatusCode statusCode;
}