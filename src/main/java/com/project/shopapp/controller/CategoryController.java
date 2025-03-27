package com.project.shopapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.CategoryRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.model.Category;
import com.project.shopapp.service.impl.CategoryServiceImpl;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    private final MessageUtils messageUtils;

    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                messageUtils.getMessage(MessageKeys.CATEGORY_LIST_SUCCESS),
                categoryService.getAllCategories());
    }

    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody @Valid CategoryRequest request) {
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                messageUtils.getMessage(MessageKeys.CATEGORY_CREATE_SUCCESS),
                categoryService.createCategory(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Category> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                messageUtils.getMessage(MessageKeys.CATEGORY_UPDATE_SUCCESS),
                categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ApiResponse<>(
                HttpStatus.OK.value(), messageUtils.getMessage(MessageKeys.CATEGORY_DELETE_SUCCESS), null);
    }
}
