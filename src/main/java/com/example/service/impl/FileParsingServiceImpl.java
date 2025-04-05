package com.example.service.impl;

//import com.example.domain.EnergyPriceData.EnergyPriceData;
//import com.example.domain.PriceCategory.PriceCategory;

import com.example.service.FileParsingService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileParsingServiceImpl implements FileParsingService {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\123\\Downloads\\Fakt_1_6_TSK_fevral_TNS_Rostov.xls"; // Путь к файлу


        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new HSSFWorkbook(fis)) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            // Получаем первый лист
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println(sheet);

            List<List<Double>> result = new ArrayList<>();
            for (Row row : sheet) {
                // Проходим по всем ячейкам строки
                List<Double> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    // Выводим значение ячейки в зависимости от её типа
                    switch (cell.getCellType()) {
//                        case STRING:
//                            System.out.print(cell.getStringCellValue() + "\t");
//                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
//                                System.out.print(cell.getDateCellValue() + ":)\t");
                            } else {
//                                System.out.print(cell.getNumericCellValue() + ":(\t");
                            }
                            break;
                        case FORMULA:
                            CellValue cellValue = evaluator.evaluate(cell);
                            switch (cellValue.getCellType()) {
                                case NUMERIC:
//                                    System.out.print(cellValue.getNumberValue() + ":|\t");
                                    rowData.add(cellValue.getNumberValue());
                                    break;
                            }
                            break;
                    }
                }
//                System.out.println(rowData);
                result.add(rowData);
//                System.out.println(); // Переход на новую строку после обработки всех ячеек
            }

            result.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    public CompletableFuture<EnergyPriceData> parseAsync(String filePath) {
//        return CompletableFuture.supplyAsync(() -> {
//            EnergyPriceData data = new EnergyPriceData();
//            try (FileInputStream fis = new FileInputStream(new File(filePath));
//                 Workbook workbook = new XSSFWorkbook(fis)) {
//
//                for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
//                    Sheet sheet = workbook.getSheetAt(sheetIndex);
//                    processSheet(sheet, data);
//                }
//
//            } catch (Exception e) {
//                // log.error("Ошибка при парсинге Excel файла", e);
//                System.out.println("Ошибка при парсинге Excel файла: " + e.getMessage());
//                throw new RuntimeException("Ошибка при парсинге Excel файла", e);
//            }
//            return data;
//        });
//    }
//
//    private void processSheet(Sheet sheet, EnergyPriceData data) {
//        sheet.forEach(row -> processRow(row, data));
//    }
//
//    private void processRow(Row row, EnergyPriceData data) {
//        if (row == null || row.getCell(0) == null) return;
//
//        String key = row.getCell(0).getStringCellValue().trim();
//        if (key.isEmpty()) return;
//
//        // Ищем начало раздела (например, "Предельный уровень нерегулируемых цен")
//        if (key.contains("Предельный уровень")) {
//            for (int i = 1; i < row.getLastCellNum(); i++) {
//                String header = row.getCell(i).getStringCellValue().trim();
//                double value = row.getCell(i + 1).getNumericCellValue();
//                data.getTransmissionCosts().put(header, value);
//            }
//        }
//
//        // Ищем почасовые данные
//        if (key.startsWith("0:00-1:00")) {
//            for (int i = 1; i < row.getLastCellNum(); i++) {
//                String timeRange = row.getCell(0).getStringCellValue().trim();
//                double value = row.getCell(i).getNumericCellValue();
//                data.getCategories().computeIfAbsent("HourlyData", k -> new PriceCategory("HourlyData"))
//                        .getEnergyPrices().put(timeRange, value);
//            }
//        }
//    }
}
