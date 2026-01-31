package com.project.shopapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.shopapp.dto.request.CartCheckoutRequest;
import com.project.shopapp.dto.request.CartItemAddRequest;
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
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
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
    public CartResponse addItem(CartItemAddRequest request) {
        Cart cart = findOrCreateCart(request.getUserId());

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

        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        cartItemRepository.save(cartItem);

        return toCartResponse(cartRepository.findById(cart.getId()).orElse(cart));
    }

    @Override
    @Transactional
    public CartResponse updateItem(Long itemId, CartItemUpdateRequest request) {
        CartItem cartItem = cartItemRepository
                .findById(itemId)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_ITEM_NOT_FOUND)));

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return toCartResponse(
                cartRepository.findById(cartItem.getCart().getId()).orElse(cartItem.getCart()));
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        CartItem cartItem = cartItemRepository
                .findById(itemId)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_ITEM_NOT_FOUND)));

        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public OrderResponse checkout(CartCheckoutRequest request) {
        Cart cart = cartRepository
                .findByUserId(request.getUserId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_NOT_FOUND)));

        List<CartItem> items = cartItemRepository.findAllByCartId(cart.getId());
        if (items.isEmpty()) {
            throw new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_EMPTY));
        }

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(request.getUserId());
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

        OrderResponse response = orderService.createOrder(orderRequest);
        cartItemRepository.deleteAllByCartId(cart.getId());

        return response;
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
        List<CartItem> items = cart.getItems() == null ? List.of() : cart.getItems();
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
