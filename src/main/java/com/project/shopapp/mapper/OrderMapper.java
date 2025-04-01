package com.project.shopapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.project.shopapp.model.dto.request.OrderRequest;
import com.project.shopapp.model.dto.response.OrderResponse;
import com.project.shopapp.model.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "trackingNumber", ignore = true)
    Order toOrder(OrderRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "trackingNumber", ignore = true)
    void updateOrderFromRequest(OrderRequest request, @MappingTarget Order order);

    @Mapping(target = "userId", source = "user.id")
    OrderResponse toOrderResponse(Order order);
}
