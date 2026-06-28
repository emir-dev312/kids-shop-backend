package com.kidsshop.backend.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailedDto(
    Long id,
    String title,
    BigDecimal price,
    String description,
    String imageUrl,
    Double averageRating,
    Integer stockCount,
    List<ReviewDto> reviews
) {}