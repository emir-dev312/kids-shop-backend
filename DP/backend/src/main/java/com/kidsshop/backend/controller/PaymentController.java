package com.kidsshop.backend.controller;

import com.kidsshop.backend.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Mock Payment System")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process/{orderId}")
    @Operation(summary = "Симуляция успешной оплаты заказа")
    public ResponseEntity<Map<String, String>> processPayment(@PathVariable Long orderId) {
        
        paymentService.processMockPayment(orderId);
        
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Оплата успешно проведена. Статус заказа изменен на PAID"
        ));
    }
}