package com.project.shopapp.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewResponse {
    Long id;
    Long userId;
    Long productId;
    Integer rating;
    String comment;
    LocalDateTime createdAt;
}
