package com.kidsshop.backend.dto;

import java.math.BigDecimal;

public record ProductShortDto(
    Long id,
    String title,
    BigDecimal price,
    String imageUrl,
    Integer stockCount
) {}