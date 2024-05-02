package com.blog.blogapi.controller;

import com.blog.blogapi.payloads.ApiResponse;
import com.blog.blogapi.payloads.CategoryDto;
import com.blog.blogapi.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id){
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<>(updateCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("Category Delete Successfully", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id){
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }
}
