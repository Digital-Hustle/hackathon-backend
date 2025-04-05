package com.example.service;

import com.example.domain.APIResponse.APIResponse;
import com.example.domain.HourlyData.HourlyData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface CalculationService {

    CompletableFuture<APIResponse> performCalculations(List<HourlyData> hourlyData);

}
