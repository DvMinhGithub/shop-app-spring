package com.project.shopapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dto.request.ProductRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.entity.Product;
import com.project.shopapp.service.impl.ProductServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductServiceImpl productService;

    @GetMapping
    public ApiResponse<List<Product>> getProducts(@RequestParam int page, @RequestParam int limit) {
        PageRequest pageRequest =
                PageRequest.of(page, limit, Sort.by("createdAt").descending());
        return ApiResponse.<List<Product>>builder()
                .code(HttpStatus.OK.value())
                .result(productService.getAllProducts(pageRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable Long id) {
        return ApiResponse.<Product>builder()
                .code(HttpStatus.OK.value())
                .result(productService.getProductById(id))
                .build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Product> createProduct(@ModelAttribute @Valid ProductRequest request) throws IOException {
        return ApiResponse.<Product>builder()
                .code(HttpStatus.CREATED.value())
                .result(productService.createProduct(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        return ApiResponse.<Product>builder()
                .code(HttpStatus.OK.value())
                .result(productService.updateProduct(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Product deleted successfully")
                .build();
    }
}
