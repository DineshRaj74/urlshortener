package com.dinesh.urlshortener.service;

import com.dinesh.urlshortener.dto.*;
import com.dinesh.urlshortener.exception.ResourceNotFoundException;
import com.dinesh.urlshortener.model.ClickEvent;
import com.dinesh.urlshortener.model.UrlMapping;
import com.dinesh.urlshortener.repository.ClickEventRepository;
import com.dinesh.urlshortener.repository.UrlMappingRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
public class UrlService {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;
    private final SecureRandom random = new SecureRandom();

    private final UrlMappingRepository urlRepository;
    private final ClickEventRepository clickRepository;
    private final String baseUrl;

    public UrlService(UrlMappingRepository urlRepository,
                      ClickEventRepository clickRepository,
                      @Value("${app.base-url}") String baseUrl) {
        this.urlRepository = urlRepository;
        this.clickRepository = clickRepository;
        this.baseUrl = trimTrailingSlash(baseUrl);
    }

    @Transactional
    public UrlResponse createShortUrl(ShortenRequest request) {
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(request.originalUrl());
        mapping.setShortCode(generateUniqueCode());
        UrlMapping saved = urlRepository.save(mapping);
        return toUrlResponse(saved);
    }

    @Transactional
    public String registerClickAndGetOriginalUrl(String shortCode, HttpServletRequest request) {
        UrlMapping mapping = getMapping(shortCode);
        mapping.setClickCount(mapping.getClickCount() + 1);

        ClickEvent event = new ClickEvent();
        event.setUrlMapping(mapping);
        event.setIpAddress(clientIp(request));
        event.setUserAgent(request.getHeader("User-Agent"));
        clickRepository.save(event);
        return mapping.getOriginalUrl();
    }

    @Transactional(readOnly = true)
    public StatsResponse getStats(String shortCode) {
        UrlMapping mapping = getMapping(shortCode);
        List<ClickResponse> clicks = clickRepository.findTop10ByUrlMappingShortCodeOrderByClickedAtDesc(shortCode)
                .stream()
                .map(c -> new ClickResponse(c.getIpAddress(), c.getUserAgent(), c.getClickedAt()))
                .toList();
        return new StatsResponse(mapping.getOriginalUrl(), mapping.getShortCode(), buildShortUrl(mapping.getShortCode()),
                mapping.getClickCount(), mapping.getCreatedAt(), clicks);
    }

    @Transactional(readOnly = true)
    public List<UrlResponse> getAllUrls() {
        return urlRepository.findAll().stream().map(this::toUrlResponse).toList();
    }

    private UrlMapping getMapping(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Short URL not found: " + shortCode));
    }

    private String generateUniqueCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for (int i = 0; i < CODE_LENGTH; i++) {
                sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
            }
            code = sb.toString();
        } while (urlRepository.existsByShortCode(code));
        return code;
    }

    private UrlResponse toUrlResponse(UrlMapping mapping) {
        return new UrlResponse(mapping.getOriginalUrl(), mapping.getShortCode(), buildShortUrl(mapping.getShortCode()),
                mapping.getClickCount(), mapping.getCreatedAt());
    }

    private String buildShortUrl(String shortCode) {
        return baseUrl + "/" + shortCode;
    }

    private String trimTrailingSlash(String value) {
        return value != null && value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }

    private String clientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
