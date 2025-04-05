package com.example.web.controllers;


import com.example.domain.APIResponse.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/price")
@RequiredArgsConstructor
public class PriceController {


    @PostMapping("/upload")
    public ResponseEntity<APIResponse> upload(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
            APIResponse response = new APIResponse("Invalid file format. Only .xls or .xlsx files are allowed", null);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            APIResponse response = new APIResponse("File uploaded successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            APIResponse response = new APIResponse("Error while processing the file: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
