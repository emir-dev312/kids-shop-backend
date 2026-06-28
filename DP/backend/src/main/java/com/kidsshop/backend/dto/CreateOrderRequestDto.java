package com.kidsshop.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateOrderRequestDto {
    @JsonProperty("userId")
    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    @JsonProperty("deliveryAddress")
    @NotBlank(message = "Адрес доставки обязателен")
    private String deliveryAddress;

    @Valid
    @JsonProperty("items")
    private List<OrderItemRequestDto> items;

    public CreateOrderRequestDto() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public List<OrderItemRequestDto> getItems() { return items; }
    public void setItems(List<OrderItemRequestDto> items) { this.items = items; }
}