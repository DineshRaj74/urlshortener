package com.dinesh.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ShortenRequest(
        @NotBlank(message = "URL is required")
        @URL(message = "Please enter a valid URL, for example https://example.com")
        String originalUrl
) {}
