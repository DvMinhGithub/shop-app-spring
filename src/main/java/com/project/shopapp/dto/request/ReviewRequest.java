package com.project.shopapp.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    @NotNull(message = "Product ID is required")
    Long productId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be from 1 to 5")
    @Max(value = 5, message = "Rating must be from 1 to 5")
    Integer rating;

    String comment;
}
