package com.example.web.controllers;

import com.example.domain.APIResponse.APIResponse;
import com.example.service.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
public class CalculateController {

    private final CalculateService calculateService;

    @PostMapping("/best-category")
    public ResponseEntity<APIResponse> calculateBestCategory(@RequestParam("file") MultipartFile file) {
        return calculateService.calculateBestCategory(file);
    }
}
