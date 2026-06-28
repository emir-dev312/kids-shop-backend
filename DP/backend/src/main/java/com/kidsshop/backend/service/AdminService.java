package com.kidsshop.backend.service;

import com.kidsshop.backend.dto.*;
import com.kidsshop.backend.entity.Order;
import com.kidsshop.backend.entity.Product;
import com.kidsshop.backend.exception.ResourceNotFoundException;
import com.kidsshop.backend.repository.OrderRepository;
import com.kidsshop.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long createProduct(ProductCreateUpdateDto request) {
        Product product = Product.builder()
                .title(request.getTitle())
                .price(request.getPrice())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .stockCount(request.getStockCount())
                .build();

        return productRepository.save(product).getId();
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(Long id, ProductCreateUpdateDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                
        product.setTitle(request.getTitle());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setStockCount(request.getStockCount());
        
        productRepository.save(product);
    }

    @Transactional
    public void updateStock(Long id, StockUpdateDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                
        product.setStockCount(request.getStockCount()); 
        productRepository.save(product);
    }

    @Transactional
    public void updateOrderStatus(Long id, OrderStatusUpdateRequestDto request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
                
        order.setStatus(request.status()); 
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }
}