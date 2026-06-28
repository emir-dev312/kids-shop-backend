package com.kidsshop.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kidsshop.backend.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderHistoryDto(
    Long id,
    OrderStatus status,
    BigDecimal totalPrice,
    
    @JsonProperty("delivery_address")
    String deliveryAddress,
    
    LocalDateTime createdAt,
    List<OrderItemHistoryDto> items
) {}