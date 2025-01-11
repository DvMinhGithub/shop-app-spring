package com.project.shopapp.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.shopapp.dto.request.ProductRequest;
import com.project.shopapp.dto.response.ProductListResponse;
import com.project.shopapp.dto.response.ProductResponse;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.mapper.ProductMapper;
import com.project.shopapp.model.Category;
import com.project.shopapp.model.Product;
import com.project.shopapp.model.ProductImage;
import com.project.shopapp.repository.CategoryRepository;
import com.project.shopapp.repository.ProductImageRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ProductServiceImpl implements ProductService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;

    ProductMapper productMapper;

    FileServiceImpl fileServiceImpl;

    @Override
    public Product createProduct(ProductRequest request) throws IOException {
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        Product product = productMapper.toProduct(request);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        List<MultipartFile> files = request.getThumbnail();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile multipartFile : files) {
                if (multipartFile.isEmpty()) {
                    continue;
                }
                createProductImage(savedProduct.getId(), multipartFile);
            }
        }

        return savedProduct;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductListResponse getAllProducts(Pageable pageRequest) {
        Page<ProductResponse> products = productRepository.findAll(pageRequest).map(product -> {
            ProductResponse productResponse = productMapper.toProductResponse(product);
            productResponse.setCategoryId(product.getCategory().getId());
            productResponse.setThumbnail(product.getThumbnail().stream()
                    .map(ProductImage::getImageUrl)
                    .toList());
            return productResponse;
        });

        ProductListResponse productListResponse = ProductListResponse.builder()
                .products(products.getContent())
                .totalPages(products.getTotalPages())
                .build();

        return productListResponse;
    }

    @Override
    public Product updateProduct(Long id, ProductRequest request) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        productMapper.toProduct(request);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    ProductImage createProductImage(Long productId, MultipartFile file) throws IOException {
        Product product =
                productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found"));

        ProductImage productImage = ProductImage.builder()
                .product(product)
                .imageUrl(fileServiceImpl.storeFile(file))
                .build();

        // max 5 images per product
        if (productImageRepository.findByProductId(productId).size() >= 5) {
            throw new DataNotFoundException("Product can have max 5 images");
        }

        return productImageRepository.save(productImage);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
