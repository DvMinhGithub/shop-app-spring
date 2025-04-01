package com.project.shopapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.project.shopapp.model.dto.request.OrderDetailRequest;
import com.project.shopapp.model.dto.response.OrderDetailResponse;
import com.project.shopapp.model.entity.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderDetail toOrderDetail(OrderDetailRequest request);

    @Mapping(target = "orderId", source = "order.id")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateOrderDetailFromRequest(OrderDetailRequest request, @MappingTarget OrderDetail orderDetail);
}
