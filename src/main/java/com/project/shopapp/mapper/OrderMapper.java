package com.project.shopapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "trackingNumber", ignore = true)
    Order toOrder(OrderRequest request);

    void updateOrderFromRequest(OrderRequest request, @MappingTarget Order order);
}
