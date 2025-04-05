package com.example.web.controllers;

import com.example.domain.Profile.History;
import com.example.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/history/{id}")
    public ResponseEntity<History> getUserHistory(@PathVariable("id") Long id) {
        return new ResponseEntity<>(profileService.getHistoryById(id), HttpStatus.OK);
    }
}
