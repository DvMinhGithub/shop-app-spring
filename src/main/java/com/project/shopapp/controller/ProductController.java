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

import com.project.shopapp.dto.request.ProductRequest;
import com.project.shopapp.dto.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping
    public ResponseEntity<String> getProducts() {
        return ResponseEntity.ok("All products");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok("Product with id " + id);
    }

    @PostMapping
    public ApiResponse<?> createProduct(@RequestBody @Valid ProductRequest request) {

        return new ApiResponse<>(200, "Product created", request);
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @RequestBody String entity) {

        return entity;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        return "Product deleted";
    }
}
