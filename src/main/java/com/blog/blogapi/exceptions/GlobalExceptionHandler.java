package com.blog.blogapi.exceptions;

import com.blog.blogapi.payloads.ApiResponse;
import com.blog.blogapi.payloads.ValidateErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, HttpStatus.NOT_FOUND);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidateErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ValidateErrorResponse response = new ValidateErrorResponse();
        Map<String, String> dict = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            System.out.println((FieldError) error);
            String fieldName = ((FieldError) error).getField();
            String fieldMessage = error.getDefaultMessage();
            dict.put(fieldName, fieldMessage);
            response.setErrors(dict);
        });

        response.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
