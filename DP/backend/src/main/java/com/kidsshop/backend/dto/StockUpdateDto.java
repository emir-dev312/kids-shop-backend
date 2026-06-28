package com.kidsshop.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class StockUpdateDto {

    @JsonProperty("stockCount")
    @NotNull(message = "не должно равняться null")
    private Integer stockCount;

    public StockUpdateDto() {}

    public Integer getStockCount() { return stockCount; }
    public void setStockCount(Integer stockCount) { this.stockCount = stockCount; }
}