package com.project.shopapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.shopapp.dto.request.ReviewRequest;
import com.project.shopapp.dto.response.ReviewResponse;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.model.entity.Order;
import com.project.shopapp.model.entity.Product;
import com.project.shopapp.model.entity.Review;
import com.project.shopapp.model.entity.User;
import com.project.shopapp.model.enums.OrderStatus;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.repository.ReviewRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.ReviewService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final MessageUtils messageUtils;

    @Override
    @Transactional
    public ReviewResponse createReview(Long userId, ReviewRequest request) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));
        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND)));

        if (!hasDeliveredProduct(userId, product.getId())) {
            throw new IllegalArgumentException("Only delivered products can be reviewed");
        }

        Review review = reviewRepository
                .findByUserIdAndProductId(userId, product.getId())
                .orElseGet(Review::new);
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return toResponse(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponse> getProductReviews(Long productId) {
        return reviewRepository.findAllByProductIdOrderByCreatedAtDesc(productId).stream()
                .map(this::toResponse)
                .toList();
    }

    private boolean hasDeliveredProduct(Long userId, Long productId) {
        for (Order order : orderRepository.findAllByUserIdAndActiveTrue(userId)) {
            if (order.getStatus() != OrderStatus.DELIVERED || order.getOrderDetails() == null) {
                continue;
            }
            boolean found = order.getOrderDetails().stream()
                    .anyMatch(detail -> detail.getProduct().getId().equals(productId));
            if (found) {
                return true;
            }
        }
        return false;
    }

    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .productId(review.getProduct().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
