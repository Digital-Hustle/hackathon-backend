package com.example.service.impl;

import com.example.domain.HourlyData.HourlyData;
import com.example.service.UserFileParsingService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserFIleParsingServiceImpl implements UserFileParsingService {

    @Async
    @Override
    public CompletableFuture<List<HourlyData>> parseFileAsync(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            List<HourlyData> hourlyDataList = new ArrayList<>();

            try (InputStream inputStream = file.getInputStream();
                 Workbook workbook = WorkbookFactory.create(inputStream)) {
                System.out.println("тут 1");
                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    Cell dayCell = row.getCell(0);
                    Cell hourCell = row.getCell(1);
                    Cell volumeCell = row.getCell(2);

                    if (dayCell == null || hourCell == null || volumeCell == null) {
                        System.out.println("Пропущена строка: " + i + " (пустые ячейки)");
                        continue;
                    }

                    int day;
                    int hour;
                    double volume;

                    try {
                        day = (int) getNumericValue(dayCell);
                        hour = (int) getNumericValue(hourCell);
                        volume = getNumericValue(volumeCell);
                    } catch (Exception e) {
                        System.out.println("Ошибка в строке: " + i + " (неверный формат данных)");
                        continue;
                    }

                    hourlyDataList.add(new HourlyData(day, hour, volume));
                }
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при парсинге файла", e);
            }

            return hourlyDataList;
        });
    }

    /**
     * Вспомогательный метод для безопасного получения числового значения из ячейки.
     */
    private double getNumericValue(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("Ячейка не может быть null");
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().replace(",", "."));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Невозможно преобразовать значение ячейки в число: " + cell.getStringCellValue());
                }
            default:
                throw new IllegalArgumentException("Неподдерживаемый тип ячейки: " + cell.getCellType());
        }
    }

}
