package com.example.service;

import com.example.domain.Profile.Report;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {
    List<Report> getReportsByUserId(Long userId);

    Report getReportById(Long id);

    Report createReport(Long userId, MultipartFile excelFile);
}
