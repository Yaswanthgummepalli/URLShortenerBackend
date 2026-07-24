package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bean.UrlMapping;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortCode(String shortCode);
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);

}