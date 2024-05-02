package com.blog.blogapi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    @NotEmpty
    @Size(min = 4, message = "Name should be at least 4 characters")
    private String name;

    @Email(message = "Enter valid Email")
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;

    @NotEmpty
    private String about;
}
