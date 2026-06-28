package com.kidsshop.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductCreateUpdateDto {
    
    @JsonProperty("title")
    @NotBlank(message = "Название обязательно")
    private String title;
    
    @JsonProperty("price")
    @NotNull(message = "Цена обязательна")
    private BigDecimal price;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("stockCount")
    @NotNull(message = "Количество на складе обязательно")
    private Integer stockCount;

    public ProductCreateUpdateDto() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getStockCount() { return stockCount; }
    public void setStockCount(Integer stockCount) { this.stockCount = stockCount; }
}