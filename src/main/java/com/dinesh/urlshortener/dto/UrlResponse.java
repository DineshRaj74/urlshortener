package com.dinesh.urlshortener.dto;

import java.time.LocalDateTime;

public record UrlResponse(
        String originalUrl,
        String shortCode,
        String shortUrl,
        long clickCount,
        LocalDateTime createdAt
) {}
