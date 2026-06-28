package com.kidsshop.backend.controller;

import com.kidsshop.backend.dto.OrderHistoryDto;
import com.kidsshop.backend.dto.UserProfileDto;
import com.kidsshop.backend.service.OrderService;
import com.kidsshop.backend.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "User profile and history")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get current user info")
    public ResponseEntity<UserProfileDto> getProfile(Authentication authentication) {
        return ResponseEntity.ok(profileService.getProfile(authentication.getName()));
    }

    @GetMapping("/orders")
    @Operation(summary = "Get user order history")
    public ResponseEntity<List<OrderHistoryDto>> getOrderHistory(Authentication authentication) {
        UserProfileDto profile = profileService.getProfile(authentication.getName());
        return ResponseEntity.ok(orderService.getOrderHistory(profile.id()));
    }
}