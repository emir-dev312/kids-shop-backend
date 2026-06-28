package com.kidsshop.backend.dto;

import java.time.LocalDateTime;

public record ReviewDto(
	String userName,
	Integer rating,
	String reviewText,
	LocalDateTime createdAt
) {}