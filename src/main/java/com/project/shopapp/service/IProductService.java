package com.project.shopapp.service;

import java.io.IOException;

import org.springframework.data.domain.Pageable;

import com.project.shopapp.dto.request.ProductRequest;
import com.project.shopapp.dto.response.ProductListResponse;
import com.project.shopapp.entity.Product;

public interface IProductService {
    Product createProduct(ProductRequest request) throws IOException;

    Product getProductById(Long id);

    ProductListResponse getAllProducts(Pageable pageRequest);

    Product updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
