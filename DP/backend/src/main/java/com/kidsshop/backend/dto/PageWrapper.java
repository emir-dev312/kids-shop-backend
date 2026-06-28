package com.kidsshop.backend.dto;

import java.util.List;

public record PageWrapper<T>(
	List<T> items,
	int currentPage,
	int totalPages
) {
	public static <T> PageWrapper<T> of(org.springframework.data.domain.Page<T> page) {
		return new PageWrapper<>(page.getContent(), page.getNumber(), page.getTotalPages());
	}
}