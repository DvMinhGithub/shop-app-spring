package com.project.shopapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.ReviewRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.dto.response.ReviewResponse;
import com.project.shopapp.security.CustomUserDetailsService;
import com.project.shopapp.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final CustomUserDetailsService userDetailsService;

    @GetMapping("/products/{productId}")
    public ApiResponse<List<ReviewResponse>> getProductReviews(@PathVariable Long productId) {
        return ApiResponse.<List<ReviewResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Product reviews fetched successfully")
                .result(reviewService.getProductReviews(productId))
                .build();
    }

    @PostMapping
    public ApiResponse<ReviewResponse> createReview(@RequestBody @Valid ReviewRequest request) {
        return ApiResponse.<ReviewResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Review saved successfully")
                .result(reviewService.createReview(userDetailsService.getCurrentUserId(), request))
                .build();
    }
}
