package com.project.shopapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.shopapp.dto.request.CartCheckoutRequest;
import com.project.shopapp.dto.request.CartItemAddRequest;
import com.project.shopapp.dto.request.CartItemMergeRequest;
import com.project.shopapp.dto.request.CartItemRequest;
import com.project.shopapp.dto.request.CartItemUpdateRequest;
import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.dto.response.CartItemResponse;
import com.project.shopapp.dto.response.CartResponse;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.model.entity.Cart;
import com.project.shopapp.model.entity.CartItem;
import com.project.shopapp.model.entity.Product;
import com.project.shopapp.model.entity.User;
import com.project.shopapp.repository.CartItemRepository;
import com.project.shopapp.repository.CartRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.CartService;
import com.project.shopapp.service.OrderService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    OrderService orderService;
    MessageUtils messageUtils;

    @Override
    public CartResponse getCart(Long userId) {
        Cart cart = findOrCreateCart(userId);
        return toCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addItem(Long userId, CartItemAddRequest request) {
        Cart cart = findOrCreateCart(userId);

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND, request.getProductId())));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElseGet(() -> {
                    CartItem item = new CartItem();
                    item.setCart(cart);
                    item.setProduct(product);
                    item.setQuantity(0L);
                    return item;
                });

        Long newQuantity = cartItem.getQuantity() + request.getQuantity();
        ensureStock(product, newQuantity);
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);

        return toCartResponse(cartRepository.findById(cart.getId()).orElse(cart));
    }

    @Override
    @Transactional
    public CartResponse updateItem(Long userId, Long itemId, CartItemUpdateRequest request) {
        Cart cart = findOrCreateCart(userId);
        CartItem cartItem = cartItemRepository
                .findByIdAndCartId(itemId, cart.getId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_ITEM_NOT_FOUND)));
        ensureStock(cartItem.getProduct(), request.getQuantity());

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return toCartResponse(
                cartRepository.findById(cartItem.getCart().getId()).orElse(cartItem.getCart()));
    }

    @Override
    @Transactional
    public void deleteItem(Long userId, Long itemId) {
        Cart cart = findOrCreateCart(userId);
        CartItem cartItem = cartItemRepository
                .findByIdAndCartId(itemId, cart.getId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_ITEM_NOT_FOUND)));

        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public CartResponse mergeCart(Long userId, List<CartItemMergeRequest> items) {
        if (items == null || items.isEmpty()) {
            return getCart(userId);
        }

        Cart cart = findOrCreateCart(userId);

        List<CartItem> existingItems = cartItemRepository.findAllByCartId(cart.getId());
        Map<Long, CartItem> existingByProductId = new HashMap<>();
        for (CartItem item : existingItems) {
            existingByProductId.put(item.getProduct().getId(), item);
        }

        Set<Long> productIds = new HashSet<>();
        for (CartItemMergeRequest item : items) {
            productIds.add(item.getProductId());
        }

        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : productRepository.findAllById(productIds)) {
            productMap.put(product.getId(), product);
        }

        for (CartItemMergeRequest requestItem : items) {
            Product product = productMap.get(requestItem.getProductId());
            if (product == null) {
                throw new DataNotFoundException(
                        messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND, requestItem.getProductId()));
            }

            CartItem cartItem = existingByProductId.get(product.getId());
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(0L);
                existingByProductId.put(product.getId(), cartItem);
            }

            Long newQuantity = cartItem.getQuantity() + requestItem.getQuantity();
            ensureStock(product, newQuantity);
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        }

        return toCartResponse(cartRepository.findById(cart.getId()).orElse(cart));
    }

    @Override
    @Transactional
    public OrderResponse checkout(Long userId, CartCheckoutRequest request) {
        Cart cart = cartRepository
                .findByUserId(userId)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_NOT_FOUND)));

        List<CartItem> items = cartItemRepository.findAllByCartId(cart.getId());
        if (items.isEmpty()) {
            throw new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_EMPTY));
        }

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setFullName(request.getFullName());
        orderRequest.setEmail(request.getEmail());
        orderRequest.setPhoneNumber(request.getPhoneNumber());
        orderRequest.setAddress(request.getAddress());
        orderRequest.setNote(request.getNote());
        orderRequest.setShippingMethod(request.getShippingMethod());
        orderRequest.setShippingAddress(request.getShippingAddress());
        orderRequest.setPaymentMethod(request.getPaymentMethod());

        List<CartItemRequest> cartItems = new ArrayList<>();
        float totalMoney = 0;
        for (CartItem item : items) {
            CartItemRequest cartItemRequest = new CartItemRequest();
            cartItemRequest.setProductId(item.getProduct().getId());
            cartItemRequest.setQuantity(item.getQuantity());
            cartItems.add(cartItemRequest);

            float price =
                    item.getProduct().getPrice() == null ? 0 : item.getProduct().getPrice();
            totalMoney += price * item.getQuantity();
        }
        orderRequest.setCartItems(cartItems);
        orderRequest.setTotalMoney(totalMoney);

        OrderResponse response = orderService.createOrder(userId, orderRequest);
        cartItemRepository.deleteAllByCartId(cart.getId());

        return response;
    }

    private void ensureStock(Product product, Long quantity) {
        long stock = product.getStock() == null ? 0L : product.getStock();
        if (quantity == null || stock < quantity) {
            throw new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_OUT_OF_STOCK));
        }
    }

    private Cart findOrCreateCart(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));

        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    private CartResponse toCartResponse(Cart cart) {
        List<CartItem> items = cart.getId() == null ? List.of() : cartItemRepository.findAllByCartId(cart.getId());
        List<CartItemResponse> responses = new ArrayList<>(items.size());
        long totalItems = 0;
        float totalMoney = 0;

        for (CartItem item : items) {
            float price =
                    item.getProduct().getPrice() == null ? 0 : item.getProduct().getPrice();
            float itemTotal = price * item.getQuantity();

            responses.add(CartItemResponse.builder()
                    .id(item.getId())
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .price(price)
                    .quantity(item.getQuantity())
                    .totalMoney(itemTotal)
                    .build());

            totalItems += item.getQuantity();
            totalMoney += itemTotal;
        }

        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(cart.getUser() == null ? null : cart.getUser().getId())
                .items(responses)
                .totalItems(totalItems)
                .totalMoney(totalMoney)
                .build();
    }
}
