package com.example.web.dto.excel;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private Integer id;
    private String fileName;
    private String fileSize;
    private String contentType;
    private LocalDateTime createdAt;
}
