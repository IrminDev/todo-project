package com.github.irmindev.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.github.irmindev.service.CacheService;

@Controller
public class ImageController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() {
        try {
            byte[] image = cacheService.getImage();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}