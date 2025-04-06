package com.example.web.dto.excel;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReportDataDto {
    private Integer id;
    private String fileName;
    private String fileSize;
    private MultipartFile data;
}
