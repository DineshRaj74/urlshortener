package com.dinesh.urlshortener.controller;

import com.dinesh.urlshortener.dto.*;
import com.dinesh.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public ResponseEntity<Void> home() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/index.html"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @PostMapping("/api/shorten")
    public ResponseEntity<ApiResponse<UrlResponse>> shorten(@Valid @RequestBody ShortenRequest request) {
        UrlResponse response = urlService.createShortUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Short URL created", response));
    }

    @GetMapping("/api/stats/{shortCode}")
    public ResponseEntity<ApiResponse<StatsResponse>> stats(@PathVariable String shortCode) {
        return ResponseEntity.ok(ApiResponse.ok("Stats fetched", urlService.getStats(shortCode)));
    }

    @GetMapping("/api/urls")
    public ResponseEntity<ApiResponse<List<UrlResponse>>> allUrls() {
        return ResponseEntity.ok(ApiResponse.ok("URLs fetched", urlService.getAllUrls()));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode, HttpServletRequest request) {
        String originalUrl = urlService.registerClickAndGetOriginalUrl(shortCode, request);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}