package com.giatrong.learning.learnspringapi.dto.response;

import com.giatrong.learning.learnspringapi.enums.SwaggerDefaultValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder // using a builder pattern to create instances of this class
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = SwaggerDefaultValue.API_RESPONSE_DESC)
public class ApiResponse<T> { // T is a generic type, can be any type of object
    
    @Schema(description = SwaggerDefaultValue.HTTP_STATUS_CODE_DESC, example = SwaggerDefaultValue.HTTP_STATUS_EXAMPLE)
    private int statusCode; // HTTP status code
    
    @Schema(description = SwaggerDefaultValue.RESPONSE_MESSAGE_DESC, example = SwaggerDefaultValue.RESPONSE_MESSAGE_EXAMPLE)
    private String message;
    
    @Schema(description = SwaggerDefaultValue.RESPONSE_DATA_DESC)
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
