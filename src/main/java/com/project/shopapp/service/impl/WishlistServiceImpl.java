package com.project.shopapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.shopapp.dto.response.WishlistItemResponse;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.model.entity.Product;
import com.project.shopapp.model.entity.User;
import com.project.shopapp.model.entity.WishlistItem;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.repository.WishlistItemRepository;
import com.project.shopapp.service.WishlistService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final MessageUtils messageUtils;

    @Override
    public List<WishlistItemResponse> getWishlist(Long userId) {
        return wishlistItemRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public WishlistItemResponse addItem(Long userId, Long productId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND)));

        WishlistItem item = wishlistItemRepository
                .findByUserIdAndProductId(userId, productId)
                .orElseGet(WishlistItem::new);
        item.setUser(user);
        item.setProduct(product);
        return toResponse(wishlistItemRepository.save(item));
    }

    @Override
    @Transactional
    public void removeItem(Long userId, Long productId) {
        WishlistItem item = wishlistItemRepository
                .findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new DataNotFoundException("Wishlist item not found"));
        wishlistItemRepository.delete(item);
    }

    private WishlistItemResponse toResponse(WishlistItem item) {
        return WishlistItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
