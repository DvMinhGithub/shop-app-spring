package com.project.shopapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.CategoryRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @GetMapping
    public ResponseEntity<String> getAllCategories() {
        return ResponseEntity.ok("All categories");
    }

    @PostMapping
    public String createCategory(@RequestBody @Valid CategoryRequest request) {

        return request.getName();
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @RequestBody String entity) {

        return entity;
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {

        return "Category deleted";
    }
}
