package com.dinesh.urlshortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "click_events")
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "url_mapping_id")
    private UrlMapping urlMapping;

    @Column(length = 80)
    private String ipAddress;

    @Column(length = 500)
    private String userAgent;

    @Column(nullable = false)
    private LocalDateTime clickedAt = LocalDateTime.now();

    public Long getId() { return id; }
    public UrlMapping getUrlMapping() { return urlMapping; }
    public void setUrlMapping(UrlMapping urlMapping) { this.urlMapping = urlMapping; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public LocalDateTime getClickedAt() { return clickedAt; }
    public void setClickedAt(LocalDateTime clickedAt) { this.clickedAt = clickedAt; }
}
