package com.example.service.impl;

import com.example.domain.APIResponse.APIResponse;
import com.example.domain.HourlyData.HourlyData;
import com.example.service.CalculationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CalculationServiceImpl implements CalculationService {

    @Async
    @Override
    public CompletableFuture<APIResponse> performCalculations(List<HourlyData> hourlyData) {
        try {
            // Пример расчета: находим максимальное значение объема
            double maxValue = hourlyData.stream()
                    .mapToDouble(HourlyData::getVolume)
                    .max()
                    .orElse(0.0);

            String message = "Best category calculated successfully. Max value: " + maxValue;
            APIResponse response = new APIResponse(message, null);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            throw new RuntimeException("Error while performing calculations", e);
        }
    }
}
