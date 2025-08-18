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
public enum ErrorCode { // enum: a special Java type used to define collections of constants

    // == Common error codes ==
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),

    // == Validation error codes ==
    INVALID_INPUT(1010, "Invalid input data", HttpStatus.BAD_REQUEST),
    VALIDATION_FAILED(1011, "Validation failed", HttpStatus.BAD_REQUEST),

    // == User error codes ==
    INVALID_KEY(1000, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    // {min} is a placeholder for the minimum length of the username or password
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    DOB_INVALID(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User is not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),

    // == Access denied ==
    ACCESS_DENIED(1009, "Access denied", HttpStatus.FORBIDDEN);


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