package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.dto.UrlRequest;
import com.example.dto.UrlResponse;
import com.example.service.UrlService;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/urls")
public class UrlController {

    @Autowired
    private UrlService service;
    //create short url
    @PostMapping
    public UrlResponse createShortUrl(@RequestBody UrlRequest request) {

        return service.createShortUrl(request);

    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        String originalUrl = service.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

}