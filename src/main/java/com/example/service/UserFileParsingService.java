package com.example.service;

import com.example.domain.HourlyData.HourlyData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface UserFileParsingService {

    CompletableFuture<List<HourlyData>> parseFileAsync(MultipartFile file);

}
