package com.project.shopapp.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

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
    MessageUtils messageUtils;

    @Override
    @Transactional
    public Product createProduct(ProductRequest request) throws IOException {
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.CATEGORY_NOT_FOUND)));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        List<MultipartFile> files = request.getThumbnail();
        List<ProductImage> productImages = createProductImages(savedProduct, files);

        savedProduct.setThumbnail(productImages);

        return savedProduct;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND)));
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

        return ProductListResponse.builder()
                .products(products.getContent())
                .totalPages(products.getTotalPages())
                .build();
    }

    @Override
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND)));
        productMapper.toProduct(request);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND)));
        productRepository.delete(product);
    }

    private List<ProductImage> createProductImages(Product product, List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        long existingImageCount = productImageRepository.countByProductId(product.getId());
        if (existingImageCount + files.size() > 5) {
            throw new RuntimeException(messageUtils.getMessage(MessageKeys.PRODUCT_IMAGE_MAX));
        }

        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .imageUrl(fileServiceImpl.storeFile(file))
                    .build();

            productImages.add(productImage);
        }

        return productImageRepository.saveAll(productImages);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
