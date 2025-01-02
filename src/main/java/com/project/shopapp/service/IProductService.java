package com.project.shopapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import com.project.shopapp.dto.request.ProductRequest;
import com.project.shopapp.entity.Product;

public interface IProductService {
    Product createProduct(ProductRequest request) throws IOException;

     @Query("SELECT p FROM Products p JOIN FETCH p.thumbnail")
    Product getProductById(Long id);

    List<Product> getAllProducts(PageRequest pageRequest);

    Product updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
