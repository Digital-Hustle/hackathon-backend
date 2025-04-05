package com.example.service;

import com.example.domain.APIResponse.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CalculateService {

    ResponseEntity<APIResponse> calculateBestCategory(MultipartFile file);

}
