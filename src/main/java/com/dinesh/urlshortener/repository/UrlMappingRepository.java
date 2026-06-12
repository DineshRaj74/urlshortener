package com.dinesh.urlshortener.repository;

import com.dinesh.urlshortener.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}
