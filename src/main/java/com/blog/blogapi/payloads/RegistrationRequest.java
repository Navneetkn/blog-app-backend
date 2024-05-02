package com.blog.blogapi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "Name is mandidatory")
    @NotBlank(message = "Name is mandidatory")
    private String name;
    @NotEmpty
    @NotBlank
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
}
