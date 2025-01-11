package com.project.shopapp.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.project.shopapp.dto.request.ProductRequest;
import com.project.shopapp.dto.response.ProductResponse;
import com.project.shopapp.model.Product;
import com.project.shopapp.model.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "thumbnail", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductRequest request);

    @Mapping(target = "thumbnail", source = "thumbnail", qualifiedByName = "productImageToStringList")
    @Mapping(target = "categoryId", source = "category.id")
    ProductResponse toProductResponse(Product product);

    @Named("productImageToStringList")
    default List<String> productImageToStringList(List<ProductImage> productImages) {
        if (productImages == null || productImages.isEmpty()) {
            return Collections.emptyList();
        }
        return productImages.stream().map(ProductImage::getImageUrl).collect(Collectors.toList());
    }
}
