package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.dto.request.ReviewRequest;
import com.project.shopapp.dto.response.ReviewResponse;

public interface ReviewService {
    ReviewResponse createReview(Long userId, ReviewRequest request);

    List<ReviewResponse> getProductReviews(Long productId);
}
