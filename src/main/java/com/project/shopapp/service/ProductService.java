package com.project.shopapp.service;

import java.io.IOException;

import org.springframework.data.domain.Pageable;

import com.project.shopapp.dto.request.ProductRequest;
import com.project.shopapp.dto.response.ProductListResponse;
import com.project.shopapp.dto.response.ProductResponse;
import com.project.shopapp.model.Product;

public interface ProductService {
    Product createProduct(ProductRequest request) throws IOException;

    ProductResponse getProductById(Long id);

    ProductListResponse getAllProducts(Long categoryId, String keyword, Pageable pageable);

    Product updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
