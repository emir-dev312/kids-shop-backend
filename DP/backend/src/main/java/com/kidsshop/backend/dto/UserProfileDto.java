package com.kidsshop.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record UserProfileDto(
    Long id,
    String name,
    String email,
    @JsonProperty("avatar_url") String avatarUrl, 
    @JsonProperty("created_at") LocalDateTime createdAt
) {}