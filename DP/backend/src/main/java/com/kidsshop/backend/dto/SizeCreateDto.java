package com.kidsshop.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SizeCreateDto {
    
    @JsonProperty("sizeValue")
    @NotBlank(message = "Размер обязателен (например, 3-мес)")
    private String sizeValue;

    @JsonProperty("stockCount")
    @NotNull(message = "Количество на складе обязательно")
    private Integer stockCount;

    public SizeCreateDto() {}

    public String getSizeValue() { return sizeValue; }
    public void setSizeValue(String sizeValue) { this.sizeValue = sizeValue; }

    public Integer getStockCount() { return stockCount; }
    public void setStockCount(Integer stockCount) { this.stockCount = stockCount; }
}