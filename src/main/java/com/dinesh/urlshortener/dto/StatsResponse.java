package com.dinesh.urlshortener.dto;

import java.time.LocalDateTime;
import java.util.List;

public record StatsResponse(
        String originalUrl,
        String shortCode,
        String shortUrl,
        long totalClicks,
        LocalDateTime createdAt,
        List<ClickResponse> recentClicks
) {}
