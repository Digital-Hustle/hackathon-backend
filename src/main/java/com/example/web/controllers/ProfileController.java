package com.example.web.controllers;

import com.example.domain.Profile.Profile;
import com.example.domain.Profile.Report;
import com.example.service.ProfileService;
import com.example.web.dto.excel.ReportDataDto;
import com.example.web.dto.excel.ReportDto;
import com.example.web.mappers.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final ReportMapper reportMapper;

    // TODO is it a good place for such method :/?
    //  Maybe should move it to other controller(Reports for exam)
    //  & actually have to implement it :)
    @PostMapping("/{userId}")
    public ResponseEntity<Profile> createReport(@PathVariable Long userId, @RequestBody MultipartFile excelFile) {

        return null;
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDataDto> getReport(@PathVariable Long reportId) {
        Report report = profileService.getReportById(reportId);
        return new ResponseEntity<>(reportMapper.toDataDto(report), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ReportDto>> getProfileReportsHistory(@PathVariable Long userId) {
        List<Report> reports = profileService.getReportsByUserId(userId);
        return new ResponseEntity<>(reportMapper.toDtoList(reports), HttpStatus.OK);
    }
}
