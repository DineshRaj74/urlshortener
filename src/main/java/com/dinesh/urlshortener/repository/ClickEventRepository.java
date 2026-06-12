package com.dinesh.urlshortener.repository;

import com.dinesh.urlshortener.model.ClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    List<ClickEvent> findTop10ByUrlMappingShortCodeOrderByClickedAtDesc(String shortCode);
}
