package com.example.web.mappers;

import com.example.domain.Profile.Report;
import com.example.utils.ByteArrayMultipartFile;
import com.example.utils.FileSizeFormatter;
import com.example.web.dto.excel.ReportDataDto;
import com.example.web.dto.excel.ReportDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        imports = com.example.utils.FileSizeFormatter.class)
public interface ReportMapper {
    @Mapping(target = "fileSize", expression = "java(FileSizeFormatter.format(report.getFileSize()))")
    ReportDto toDto(Report report);

    default ReportDataDto toDataDto(Report report) {
        if (report == null) return null;

        ReportDataDto reportDataDto = new ReportDataDto();

        // TODO finish with it. map byte[] -> MultipartFile. Upd.: refactor .setData(...)
        reportDataDto.setId(report.getId());
        reportDataDto.setFileName(report.getFileName());
        reportDataDto.setFileSize(FileSizeFormatter.format(report.getFileSize()));
        reportDataDto.setData(
                new ByteArrayMultipartFile(
                        report.getData(),
                        report.getFileName(),
                        report.getFileName(),
                        report.getContentType()
                )
        );

        return reportDataDto;
    }

    Report toEntity(ReportDto reportDto);

    default List<ReportDto> toDtoList(List<Report> reports) {
        if (reports == null) return Collections.emptyList();
        return reports.stream().map(this::toDto).collect(Collectors.toList());
    }
}
