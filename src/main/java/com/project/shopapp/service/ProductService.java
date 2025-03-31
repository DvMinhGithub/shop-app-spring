package com.project.shopapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.project.shopapp.model.dto.request.ProductRequest;
import com.project.shopapp.model.dto.response.ProductListResponse;
import com.project.shopapp.model.dto.response.ProductResponse;
import com.project.shopapp.model.entity.Product;

public interface ProductService {
    Product createProduct(ProductRequest request) throws IOException;

    ProductResponse getProductById(Long id);

    List<ProductResponse> getProductByIds(String ids);

    ProductListResponse getAllProducts(Long categoryId, String keyword, Pageable pageable);

    Product updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
