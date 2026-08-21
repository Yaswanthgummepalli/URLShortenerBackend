package com.example.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import com.example.dto.UrlRequest;
import com.example.dto.UrlResponse;
import com.example.bean.UrlMapping;
import com.example.repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

@Autowired
private HttpServletRequest request;
	private String getBaseUrl() {
	    return request.getScheme() + "://"
	            + request.getServerName()
	            + (request.getServerPort() == 80 || request.getServerPort() == 443
	                ? ""
	                : ":" + request.getServerPort());
	}


  public UrlResponse createShortUrl(UrlRequest request) {
        String originalUrl=request.getOriginalUrl().trim();

        Optional<UrlMapping> existingUrl=repository.findByOriginalUrl(originalUrl);
        if(existingUrl.isPresent())
        {
                return new UrlResponse(getBaseUrl()+"/api/urls/"+existingUrl.get().getShortCode());
        }
        String shortCode = UUID.randomUUID()
                               .toString()
                               .substring(0, 6);


        UrlMapping url = new UrlMapping();

        url.setOriginalUrl(originalUrl);

        url.setShortCode(shortCode);

        url.setCreatedAt(LocalDateTime.now());


        repository.save(url);


        return new UrlResponse(getBaseUrl()+"/api/urls/" + shortCode);

    }
    public String getOriginalUrl(String shortCode) {

        UrlMapping url = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        return url.getOriginalUrl();
    }

}