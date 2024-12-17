package com.project.shopapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dto.request.CategoryRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.entity.Category;
import com.project.shopapp.service.impl.CategoryServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {

        return new ApiResponse<>(HttpStatus.OK.value(), null, categoryService.getAllCategories());
    }

    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody @Valid CategoryRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), null, categoryService.createCategory(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {

        return new ApiResponse<>(HttpStatus.OK.value(), null, categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Category deleted successfully", null);
    }
}
