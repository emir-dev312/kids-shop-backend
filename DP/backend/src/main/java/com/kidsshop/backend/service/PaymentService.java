package com.kidsshop.backend.service;

import com.kidsshop.backend.entity.Order;
import com.kidsshop.backend.entity.OrderStatus;
import com.kidsshop.backend.exception.ResourceNotFoundException;
import com.kidsshop.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    @Transactional
    public void processMockPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Проверяем статус напрямую через Enum
        if (order.getStatus() == OrderStatus.PAID) {
            throw new IllegalStateException("Order is already paid!");
        }

        // Правильный вызов сеттера с передачей Enum-значения
        order.setStatus(OrderStatus.PAID); 
        
        orderRepository.save(order);
    }
}