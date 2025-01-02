package com.project.shopapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.shopapp.dto.request.ProductRequest;
import com.project.shopapp.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "thumbnail", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductRequest request);
}
