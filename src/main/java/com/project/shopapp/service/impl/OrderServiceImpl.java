package com.project.shopapp.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.shopapp.dto.request.CartItemRequest;
import com.project.shopapp.dto.request.OrderRequest;
import com.project.shopapp.dto.response.OrderResponse;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.ForbiddenException;
import com.project.shopapp.mapper.OrderMapper;
import com.project.shopapp.model.entity.Order;
import com.project.shopapp.model.entity.OrderDetail;
import com.project.shopapp.model.entity.Product;
import com.project.shopapp.model.entity.User;
import com.project.shopapp.model.enums.OrderStatus;
import com.project.shopapp.repository.OrderDetailRepository;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.repository.UserRepository;
import com.project.shopapp.service.OrderService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    UserRepository userRepository;
    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    ProductRepository productRepository;
    MessageUtils messageUtils;
    OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));

        LocalDateTime shippingDate =
                request.getShippingDate() == null ? LocalDateTime.now().plusDays(1) : request.getShippingDate();

        if (shippingDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    messageUtils.getMessage(MessageKeys.SHIPPING_DATE_MUST_BE_GREATER_THAN_ORDER_DATE));
        }

        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setShippingDate(shippingDate);
        order.setActive(true);
        order.setStatus(OrderStatus.PENDING);

        if (request.getCartItems() == null || request.getCartItems().isEmpty()) {
            throw new DataNotFoundException(messageUtils.getMessage(MessageKeys.CART_EMPTY));
        }

        Order savedOrder = orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (CartItemRequest cartItemRequest : request.getCartItems()) {
            if (cartItemRequest.getQuantity() == null || cartItemRequest.getQuantity() < 1) {
                throw new IllegalArgumentException("Quantity must be at least 1");
            }
            Product product = productRepository
                    .findById(cartItemRequest.getProductId())
                    .orElseThrow(() -> new DataNotFoundException(
                            messageUtils.getMessage(MessageKeys.PRODUCT_NOT_FOUND, cartItemRequest.getProductId())));
            reduceStock(product, cartItemRequest.getQuantity());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setQuantity(cartItemRequest.getQuantity());
            orderDetail.setPrice(product.getPrice());
            orderDetail.setTotalMoney(product.getPrice() * cartItemRequest.getQuantity());
            orderDetail.setProduct(product);

            orderDetails.add(orderDetailRepository.save(orderDetail));
        }
        savedOrder.setOrderDetails(orderDetails);

        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> findByUserId(Long userId) {
        return orderRepository.findAllByUserIdAndActiveTrue(userId).stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<OrderResponse> findAllByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findAllByKeyword(keyword, pageable).map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        return orderRepository
                .findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));
    }

    @Override
    public OrderResponse getOrderForUser(Long id, Long userId, boolean admin) {
        Order order = orderRepository
                .findById(id)
                .filter(Order::isActive)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));
        if (!admin && !order.getUser().getId().equals(userId)) {
            throw new ForbiddenException(messageUtils.getMessage(MessageKeys.FORBIDDEN));
        }
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        Order existOrder = orderRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));

        User existUser = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.USER_NOT_FOUND)));

        LocalDateTime shippingDate =
                request.getShippingDate() == null ? existOrder.getShippingDate() : request.getShippingDate();

        if (shippingDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    messageUtils.getMessage(MessageKeys.SHIPPING_DATE_MUST_BE_GREATER_THAN_ORDER_DATE));
        }

        orderMapper.updateOrderFromRequest(request, existOrder);

        existOrder.setUser(existUser);
        existOrder.setShippingDate(shippingDate);

        return orderMapper.toOrderResponse(orderRepository.save(existOrder));
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository
                .findById(id)
                .filter(Order::isActive)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));
        if (!isValidTransition(order.getStatus(), status)) {
            throw new IllegalArgumentException(messageUtils.getMessage(MessageKeys.ORDER_STATUS_INVALID));
        }
        if (status == OrderStatus.CANCELLED) {
            restoreStock(order);
        }
        order.setStatus(status);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long id, Long userId, boolean admin) {
        Order order = orderRepository
                .findById(id)
                .filter(Order::isActive)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));
        if (!admin && !order.getUser().getId().equals(userId)) {
            throw new ForbiddenException(messageUtils.getMessage(MessageKeys.FORBIDDEN));
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException(messageUtils.getMessage(MessageKeys.ORDER_STATUS_INVALID));
        }
        restoreStock(order);
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        Order existOrder = orderRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ORDER_NOT_FOUND)));

        existOrder.setActive(false);
        orderRepository.save(existOrder);
    }

    private void reduceStock(Product product, Long quantity) {
        long stock = product.getStock() == null ? 0L : product.getStock();
        if (quantity == null || stock < quantity) {
            throw new DataNotFoundException(messageUtils.getMessage(MessageKeys.PRODUCT_OUT_OF_STOCK));
        }
        product.setStock(stock - quantity);
        productRepository.save(product);
    }

    private void restoreStock(Order order) {
        if (order.getStatus() == OrderStatus.CANCELLED || order.getOrderDetails() == null) {
            return;
        }
        for (OrderDetail detail : order.getOrderDetails()) {
            Product product = detail.getProduct();
            long stock = product.getStock() == null ? 0L : product.getStock();
            product.setStock(stock + detail.getQuantity());
            productRepository.save(product);
        }
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        if (current == null || next == null || current == next) {
            return false;
        }
        return switch (current) {
            case PENDING -> next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELLED;
            case CONFIRMED -> next == OrderStatus.SHIPPING || next == OrderStatus.CANCELLED;
            case SHIPPING -> next == OrderStatus.DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };
    }
}
