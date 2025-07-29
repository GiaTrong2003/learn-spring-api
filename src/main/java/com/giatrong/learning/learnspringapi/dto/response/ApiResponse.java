package com.giatrong.learning.learnspringapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder // using a builder pattern to create instances of this class
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> { // T is a generic type, can be any type of object
    private int statusCode; // HTTP status code
    private String message;
    private T data; // Generic type to hold any data, can be null if no data is returned

    // == Factory methods convenient ==

    // == Success response ==
    public static <T> ApiResponse<T> success(T data, String message, int statusCode) {
        return ApiResponse.<T>builder().statusCode(statusCode).message(message).data(data).build();
        // when you call builder() -> it is building an instance of ApiResponse<T> -> using a builder pattern
    }

    public static <T> ApiResponse<List<T>> success(List<T> data, String message, int statusCode) {
        return ApiResponse.<List<T>>builder().statusCode(statusCode).message(message).data(data).build();
    }

    // == Failure response ==
    // <?> meaning any type, but we don't care about the type
    // <T> meaning we care about the type, it will compiler and convert to Object type
    public static ApiResponse<?> failure(String message, int StatusCode) {
        return ApiResponse.builder().statusCode(StatusCode).message(message).build();
    }


    // == Error response ==
    public static ApiResponse<?> error(int statusCode, String message) {
        return ApiResponse.builder().statusCode(statusCode).message(message).build();
    }

}
