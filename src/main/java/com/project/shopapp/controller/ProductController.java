package com.project.shopapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.github.javafaker.Faker;
import com.project.shopapp.model.dto.request.ProductRequest;
import com.project.shopapp.model.dto.response.ApiResponse;
import com.project.shopapp.model.dto.response.ProductListResponse;
import com.project.shopapp.model.dto.response.ProductResponse;
import com.project.shopapp.model.entity.Product;
import com.project.shopapp.service.impl.ProductServiceImpl;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final MessageUtils messageUtils;

    @GetMapping
    public ApiResponse<ProductListResponse> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Long categoryId) {
        Pageable pageRequest = PageRequest.of(page - 1, limit);
        return ApiResponse.<ProductListResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.PRODUCT_LIST_SUCCESS))
                .result(productService.getAllProducts(categoryId, keyword, pageRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.PRODUCT_DETAIL_SUCCESS))
                .result(productService.getProductById(id))
                .build();
    }

    @GetMapping("/ids")
    public ApiResponse<List<ProductResponse>> getProductsByIds(@RequestParam("ids") String ids) {
        return ApiResponse.<List<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.PRODUCT_LIST_SUCCESS))
                .result(productService.getProductByIds(ids))
                .build();
    }

    @PostMapping(consumes = "multipart/form-data")
    public ApiResponse<Product> createProduct(@ModelAttribute ProductRequest request) throws IOException {
        return ApiResponse.<Product>builder()
                .code(HttpStatus.CREATED.value())
                .message(messageUtils.getMessage(MessageKeys.PRODUCT_CREATE_SUCCESS))
                .result(productService.createProduct(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        return ApiResponse.<Product>builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.PRODUCT_UPDATE_SUCCESS))
                .result(productService.updateProduct(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message(messageUtils.getMessage(MessageKeys.PRODUCT_DELETE_SUCCESS))
                .build();
    }

    @PostMapping("/generateFakeProducts")
    public ApiResponse<Void> generateFakeProducts() throws IOException {
        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            String name = faker.commerce().productName();

            if (productService.existsByName(name)) {
                continue;
            }

            ProductRequest productRequest = ProductRequest.builder()
                    .name(name)
                    .description(faker.lorem().sentence())
                    .price(faker.number().randomDouble(2, 1, 1000000))
                    .categoryId((long) faker.number().numberBetween(1, 4))
                    .build();

            try {
                productService.createProduct(productRequest);
            } catch (Exception e) {
                return ApiResponse.<Void>builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(messageUtils.getMessage(MessageKeys.PRODUCT_GENERATE_ERROR))
                        .build();
            }
        }
        return ApiResponse.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message(messageUtils.getMessage(MessageKeys.PRODUCT_GENERATE_SUCCESS))
                .build();
    }
}
