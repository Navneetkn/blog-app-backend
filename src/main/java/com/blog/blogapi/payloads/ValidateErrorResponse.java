package com.blog.blogapi.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateErrorResponse {
    private Map<String, String> errors;
    private HttpStatus status;
}
