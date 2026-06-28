package com.kidsshop.backend.controller;

import com.kidsshop.backend.dto.PageWrapper;
import com.kidsshop.backend.dto.ProductDetailedDto;
import com.kidsshop.backend.dto.ProductShortDto;
import com.kidsshop.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Catalog", description = "Public catalog access")
public class ProductController {

	private final ProductService productService;

	@GetMapping
	@Operation(summary = "Get paginated products")
	public ResponseEntity<PageWrapper<ProductShortDto>> getProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(productService.getProducts(page, size));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get product details with sizes and reviews")
	public ResponseEntity<ProductDetailedDto> getProductById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}
}