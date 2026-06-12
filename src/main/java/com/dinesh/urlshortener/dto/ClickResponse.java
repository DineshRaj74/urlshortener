package com.dinesh.urlshortener.dto;

import java.time.LocalDateTime;

public record ClickResponse(String ipAddress, String userAgent, LocalDateTime clickedAt) {}
