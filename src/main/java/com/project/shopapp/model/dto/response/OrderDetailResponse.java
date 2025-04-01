package com.project.shopapp.model.dto.response;

import com.project.shopapp.model.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;
    Long orderId;
    Product product;
    Float price;
    Long quantity;
    Long totalMoney;
    String color;
}
