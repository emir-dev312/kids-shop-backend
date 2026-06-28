package com.kidsshop.backend.service;

import com.kidsshop.backend.dto.*;
import com.kidsshop.backend.entity.Product;
import com.kidsshop.backend.exception.ResourceNotFoundException;
import com.kidsshop.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long createProduct(ProductCreateUpdateDto dto) {

        Product product = Product.builder()
                .title(dto.getTitle())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .stockCount(dto.getStockCount()) // Берем остатки напрямую из DTO
                .build();
        
        Product savedProduct = productRepository.save(product);
        
        return savedProduct.getId();
    }

    @Transactional(readOnly = true)
    public PageWrapper<ProductShortDto> getProducts(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        
        List<ProductShortDto> items = productPage.getContent().stream()
                .map(p -> new ProductShortDto(
                        p.getId(),
                        p.getTitle(),
                        p.getPrice(),
                        p.getImageUrl(),
                        p.getStockCount() // Заменили список размеров на stockCount
                ))
                .collect(Collectors.toList());

        return new PageWrapper<>(items, productPage.getNumber(), productPage.getTotalPages());
    }

    @Transactional(readOnly = true)
    public ProductDetailedDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        double averageRating = product.getReviews().stream()
                .mapToInt(review -> review.getRating())
                .average()
                .orElse(0.0);

        BigDecimal roundedRating = BigDecimal.valueOf(averageRating).setScale(1, RoundingMode.HALF_UP);

        List<ReviewDto> reviews = product.getReviews().stream()
                .map(r -> new ReviewDto(
                        r.getUser().getName(),
                        r.getRating(),
                        r.getReviewText(),
                        r.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new ProductDetailedDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                roundedRating.doubleValue(),
                product.getStockCount(), // Заменили список размеров на stockCount
                reviews
        );
    }
}