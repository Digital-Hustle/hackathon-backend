package com.example.web.controllers;


import com.example.domain.APIResponse.APIResponse;
import com.example.domain.FileParse.PriceRecord;
import com.example.service.FileParsingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v1/price")
@RequiredArgsConstructor
@Tag(name = "Price Controller", description = "Price API")
public class PriceController {

    private final FileParsingService fileParsingService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse> upload(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
            APIResponse response = new APIResponse("Invalid file format. Only .xls or .xlsx files are allowed", null);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            CompletableFuture<List<PriceRecord>> future = fileParsingService.parseExcelFile(file.getInputStream(), file.getOriginalFilename());
            List<PriceRecord> priceRecords = future.join();

            APIResponse response = new APIResponse("File uploaded successfully", priceRecords);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("кэтч контролера");
            e.printStackTrace();
            APIResponse response = new APIResponse("Error while processing the file", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

