package com.kidsshop.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class OrderItemRequestDto {
    
    @JsonProperty("productId")
    @NotNull(message = "ID товара обязателен")
    private Long productId;

    @JsonProperty("quantity")
    @NotNull(message = "Количество обязательно")
    private Integer quantity;

    public OrderItemRequestDto() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}