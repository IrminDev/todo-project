package com.github.irmindev.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

@Service
public class CacheService {

    private static final String CACHE_FILE = "/tmp/cached_image.jpg";
    private static final long CACHE_DURATION = 60 * 60 * 1000; // 60 minutes in milliseconds

    public byte[] getImage() throws IOException {
        Path cachePath = Paths.get(CACHE_FILE);
        if (Files.exists(cachePath)) {
            FileTime lastModifiedTime = Files.getLastModifiedTime(cachePath);
            Instant expirationTime = lastModifiedTime.toInstant().plusMillis(CACHE_DURATION);
            if (Instant.now().isBefore(expirationTime)) {
                return Files.readAllBytes(cachePath);
            }
        }
        byte[] imageData = fetchImageFromUrl("https://picsum.photos/1200");
        Files.write(cachePath, imageData);
        return imageData;
    }

    private byte[] fetchImageFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try (InputStream inputStream = connection.getInputStream()) {
            return StreamUtils.copyToByteArray(inputStream);
        }
    }
}