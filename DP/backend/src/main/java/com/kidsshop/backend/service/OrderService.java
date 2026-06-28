package com.kidsshop.backend.service;

import com.kidsshop.backend.dto.*;
import com.kidsshop.backend.entity.*;
import com.kidsshop.backend.exception.ResourceNotFoundException;
import com.kidsshop.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    // ProductSizeRepository удален
    private final UserRepository userRepository;

    @Transactional
    public Long createOrder(CreateOrderRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.NEW) 
                .totalPrice(BigDecimal.ZERO)
                .deliveryAddress(request.getDeliveryAddress()) 
                .items(new ArrayList<>())
                .build();

        BigDecimal calculatedTotalPrice = BigDecimal.ZERO;

        for (OrderItemRequestDto itemDto : request.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            // Проверяем остатки напрямую у товара
            if (product.getStockCount() < itemDto.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product ID: " + itemDto.getProductId());
            }

            // Вычитаем остатки у товара
            product.setStockCount(product.getStockCount() - itemDto.getQuantity());

            BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            calculatedTotalPrice = calculatedTotalPrice.add(itemPrice);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    // Убрано .sizeValue(itemDto.getSizeValue())
                    .quantity(itemDto.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();

            order.getItems().add(orderItem);
        }

        order.setTotalPrice(calculatedTotalPrice);
        return orderRepository.save(order).getId();
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryDto> getOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

        return orders.stream()
                .map(order -> new OrderHistoryDto(
                        order.getId(),
                        order.getStatus(),
                        order.getTotalPrice(),
                        order.getDeliveryAddress(), 
                        order.getCreatedAt(),
                        order.getItems().stream()
                                .map(item -> new OrderItemHistoryDto(
                                        item.getProduct().getId(),
                                        item.getProduct().getTitle(),
                                        item.getProduct().getImageUrl()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}