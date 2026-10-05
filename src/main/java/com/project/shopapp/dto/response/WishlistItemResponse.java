package com.project.shopapp.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WishlistItemResponse {
    Long id;
    Long productId;
    String productName;
    Float price;
    LocalDateTime createdAt;
}
