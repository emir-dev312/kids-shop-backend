package com.kidsshop.backend.controller;

import com.kidsshop.backend.dto.CreateOrderRequestDto;
import com.kidsshop.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order processing")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	@Operation(summary = "Create a new order")
	public ResponseEntity<Long> createOrder(@Valid @RequestBody CreateOrderRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
	}
}