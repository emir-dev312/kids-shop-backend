package com.kidsshop.backend.dto;

import com.kidsshop.backend.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequestDto(
	@NotNull OrderStatus status
) {}