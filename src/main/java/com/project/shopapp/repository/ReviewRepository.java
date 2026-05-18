package com.project.shopapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.model.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductIdOrderByCreatedAtDesc(Long productId);

    Optional<Review> findByUserIdAndProductId(Long userId, Long productId);
}
