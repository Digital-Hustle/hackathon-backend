package com.example.service.impl;

import com.example.domain.Profile.Report;
import com.example.domain.exception.ResourceNotFoundException;
import com.example.domain.user.User;
import com.example.repository.ReportRepository;
import com.example.repository.UserRepository;
import com.example.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;


    @Override
    public List<Report> getReportsByUserId(Long userId) {

        return List.of();
    }

    @Override
    public Report createReport(Long userId, MultipartFile excelFile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (excelFile.isEmpty()) {
            throw new IllegalArgumentException("Excel file is empty");
        }

        String contentType = excelFile.getContentType();
        if (!isExcelContentType(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Expected Excel file");
        }

        Report report = new Report();
        try {
            report.setUser(user);
            report.setData(excelFile.getBytes());
            report.setFileName(excelFile.getOriginalFilename());
            report.setFileSize(excelFile.getSize());
            report.setContentType(contentType);

            if (user.getReports() != null) {
                user.getReports().add(report);
            }

            return reportRepository.save(report);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file content", e);
        }
    }

    private boolean isExcelContentType(String contentType) {
        return contentType != null && (contentType.equals("application/vnd.ms-excel") ||
                contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }
}
