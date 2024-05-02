package com.blog.blogapi.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer category_id;

    @NotEmpty
    @Size(min = 4, max = 50)
    private String categoryTitle;

    @NotEmpty
    @Size(min = 10, max = 100)
    private String categoryDescription;
}
