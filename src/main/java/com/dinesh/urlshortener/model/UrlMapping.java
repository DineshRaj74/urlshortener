package com.dinesh.urlshortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "url_mappings", indexes = {
        @Index(name = "idx_short_code", columnList = "shortCode", unique = true)
})
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 20)
    private String shortCode;

    @Column(nullable = false)
    private long clickCount = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "urlMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClickEvent> clickEvents = new ArrayList<>();

    public Long getId() { return id; }
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }
    public long getClickCount() { return clickCount; }
    public void setClickCount(long clickCount) { this.clickCount = clickCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<ClickEvent> getClickEvents() { return clickEvents; }
    public void setClickEvents(List<ClickEvent> clickEvents) { this.clickEvents = clickEvents; }
}
