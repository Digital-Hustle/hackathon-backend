package com.example.service.impl;

import com.example.domain.Profile.Report;
import com.example.domain.exception.ResourceNotFoundException;
import com.example.domain.user.User;
import com.example.repository.ReportRepository;
import com.example.service.ProfileService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserService userService;
    private final ReportRepository reportRepository;


    @Override
    public List<Report> getReportsByUserId(Long userId) {
        return userService.getById(userId).getReports();
    }

    @Override
    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow(
                () -> new ResourceNotFoundException("Report not found")
        );
    }

    @Override
    @Transactional
    public Report createReport(Long userId, MultipartFile excelFile) {
        User user = userService.getById(userId);

        if (excelFile.isEmpty())
            throw new IllegalArgumentException("Excel file is empty");

        String contentType = excelFile.getContentType();
        if (!isExcelContentType(contentType))
            throw new IllegalArgumentException("Invalid file type. Expected Excel file");

        Report report = new Report();
        try {
            report.setUser(user);
            report.setData(excelFile.getBytes());
            report.setFileName(excelFile.getOriginalFilename());
            report.setFileSize(excelFile.getSize());
            report.setContentType(contentType);

            if (user.getReports() != null)
                user.getReports().add(report);

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
