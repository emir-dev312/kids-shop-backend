package com.kidsshop.backend.controller;

import com.kidsshop.backend.dto.LoginRequestDto;
import com.kidsshop.backend.dto.RegisterRequestDto;
import com.kidsshop.backend.dto.TokenResponseDto;
import com.kidsshop.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication and registration")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	@Operation(summary = "Register new user")
	public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
	}

	@PostMapping("/login")
	@Operation(summary = "Login and receive JWT")
	public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
		return ResponseEntity.ok(authService.login(request));
	}
}