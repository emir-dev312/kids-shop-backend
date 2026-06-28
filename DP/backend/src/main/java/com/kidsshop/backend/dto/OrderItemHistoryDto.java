package com.kidsshop.backend.dto;

public record OrderItemHistoryDto(
	Long productId,
	String title,
	String imageUrl
) {}