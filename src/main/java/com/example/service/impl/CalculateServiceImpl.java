package com.example.service.impl;

import com.example.domain.APIResponse.APIResponse;
import com.example.domain.HourlyData.HourlyData;
import com.example.service.CalculateService;
import com.example.service.CalculationService;
import com.example.service.UserFileParsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    private final UserFileParsingService userFileParsingService;
    private final CalculationService calculationService;

    @Override
    public ResponseEntity<APIResponse> calculateBestCategory(MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
            APIResponse response = new APIResponse("Invalid file format. Only .xls or .xlsx files are allowed", null);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Асинхронный парсинг файла
            CompletableFuture<List<HourlyData>> parsedDataFuture = userFileParsingService.parseFileAsync(file);
            List<HourlyData> parsedData = parsedDataFuture.join();

            APIResponse response = new APIResponse("File parsed successfully", parsedData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            APIResponse response = new APIResponse("Error while processing the file: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
