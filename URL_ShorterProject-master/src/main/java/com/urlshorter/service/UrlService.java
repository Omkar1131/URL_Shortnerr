package com.urlshorter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opsmatters.bitly.Bitly;
import com.opsmatters.bitly.api.model.v4.CreateBitlinkResponse;

import jakarta.annotation.PostConstruct;

@Service
public class UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlService.class);

    @Value("${BITLY_TOKEN}")
    private String BITLY_TOKEN;

    private Bitly bitly;

    @PostConstruct
    public void setup() {
        bitly = new Bitly(BITLY_TOKEN);
    }

    public String getShortUrl(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            log.error("Invalid long URL provided.");
            return "Invalid URL provided.";
        }

        try {
            CreateBitlinkResponse response = bitly.bitlinks().shorten(longUrl).get();
            String shortLink = response.getLink();
            log.info("Successfully shortened URL: {}", shortLink);
            return shortLink;
        } catch (Exception e) {
            log.error("Error shortening URL: {}", e.getMessage());
            return "Failed to shorten the URL.";
        }
    }

    public String getBITLY_TOKEN() {
        return BITLY_TOKEN;
    }

    public void setBITLY_TOKEN(String bITLY_TOKEN) {
        BITLY_TOKEN = bITLY_TOKEN;
    }

    public Bitly getBitly() {
        return bitly;
    }

    public void setBitly(Bitly bitly) {
        this.bitly = bitly;
    }
}