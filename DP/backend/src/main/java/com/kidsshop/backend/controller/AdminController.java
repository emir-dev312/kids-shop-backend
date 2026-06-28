package com.kidsshop.backend.controller;

import com.kidsshop.backend.dto.OrderStatusUpdateRequestDto;
import com.kidsshop.backend.dto.ProductCreateUpdateDto;
import com.kidsshop.backend.dto.StockUpdateDto;
import com.kidsshop.backend.entity.Order;
import com.kidsshop.backend.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Administrator operations")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/products")
    @Operation(summary = "Create new product")
    public ResponseEntity<Long> createProduct(@Valid @RequestBody ProductCreateUpdateDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createProduct(request));
    }

    @PutMapping("/products/{id}")
    @Operation(summary = "Update product info")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductCreateUpdateDto request) {
        adminService.updateProduct(id, request);
        return ResponseEntity.ok().build();
    }

    // Изменили путь, так как sizes больше нет
    @PutMapping("/products/{id}/stock")
    @Operation(summary = "Update product stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @Valid @RequestBody StockUpdateDto request) {
        adminService.updateStock(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    @Operation(summary = "Get paginated orders list")
    public ResponseEntity<Page<Order>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(adminService.getAllOrders(page, size));
    }

    @PatchMapping("/orders/{id}/status")
    @Operation(summary = "Change order status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateRequestDto request) {
        adminService.updateOrderStatus(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    @Operation(summary = "Delete product")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}