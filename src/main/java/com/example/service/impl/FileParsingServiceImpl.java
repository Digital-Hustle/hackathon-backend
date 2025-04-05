package com.example.service.impl;

import com.example.domain.FileParse.PriceRecord;
import com.example.service.FileParsingService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class FileParsingServiceImpl implements FileParsingService {

    @Async
    public CompletableFuture<List<PriceRecord>> parseExcelFile(InputStream inputStream, String fileName) {
        return CompletableFuture.supplyAsync(() -> {
            List<PriceRecord> priceRecords = new ArrayList<>();
            try (Workbook workbook = getWorkbook(inputStream, fileName)) {
                Sheet sheet = workbook.getSheetAt(0);
                if (sheet == null) {
                    throw new IllegalArgumentException("Первый лист не найден в файле.");
                }

                boolean isThirdCategorySection = false;
                String currentDate = null;
                String currentVoltageLevel = null; // Уровень напряжения

                // Создаем FormulaEvaluator для вычисления формул
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                for (Row row : sheet) {
                    // Поиск начала раздела
                    if (row.getCell(0) != null && row.getCell(0).toString().contains("III. ТРЕТЬЯ ЦЕНОВАЯ КАТЕГОРИЯ")) {
                        isThirdCategorySection = true;
                        System.out.println("Найден раздел 'III. ТРЕТЬЯ ЦЕНОВАЯ КАТЕГОРИЯ'");
                        continue;
                    }

                    // Проверка конца раздела
                    if (row.getCell(0) != null && row.getCell(0).toString().contains("Ставка за мощность предельного уровня нерегулируемых цен")) {
                        break; // Завершаем парсинг
                    }

                    if (!isThirdCategorySection) {
                        continue; // Пропускаем строки до нужной секции
                    }

                    // Поиск уровня напряжения
                    if (row.getCell(0) != null && (row.getCell(0).toString().contains("СН I") ||
                            row.getCell(0).toString().contains("СН II") ||
                            row.getCell(0).toString().contains("ВН"))) {
                        currentVoltageLevel = row.getCell(0).toString();
                        System.out.println("Найден уровень напряжения: " + currentVoltageLevel);
                        continue;
                    }

                    // Извлечение даты
                    if (row.getCell(0) != null && row.getCell(0).getCellType() == CellType.NUMERIC) {
                        int date = (int) row.getCell(0).getNumericCellValue();
                        currentDate = String.valueOf(date);
                        System.out.println("Извлечена дата: " + currentDate);
                        continue;
                    }

                    // Парсим данные по часам
                    if (currentDate != null && row.getCell(0) != null) {
                        for (int colIndex = 1; colIndex <= 24; colIndex++) { // Столбцы с 1 по 24 (по часам)
                            Cell timeCell = row.getCell(colIndex);
                            if (timeCell != null) {
                                switch (timeCell.getCellType()) {
                                    case NUMERIC:
                                        double price = timeCell.getNumericCellValue();
                                        int hour = colIndex - 1; // Индекс столбца соответствует часу (0-23)
                                        priceRecords.add(new PriceRecord(currentDate, hour, price, currentVoltageLevel));
                                        System.out.println("Добавлена запись: дата=" + currentDate + ", час=" + hour + ", цена=" + price + ", уровень напряжения=" + currentVoltageLevel);
                                        break;

                                    case FORMULA:
                                        // Вычисляем значение формулы
                                        CellValue evaluatedValue = evaluator.evaluate(timeCell);
                                        if (evaluatedValue.getCellType() == CellType.NUMERIC) {
                                            double formulaPrice = evaluatedValue.getNumberValue();
                                            int formulaHour = colIndex - 1;
                                            priceRecords.add(new PriceRecord(currentDate, formulaHour, formulaPrice, currentVoltageLevel));
                                            System.out.println("Добавлена запись (формула): дата=" + currentDate + ", час=" + formulaHour + ", цена=" + formulaPrice + ", уровень напряжения=" + currentVoltageLevel);
                                        }
                                        break;

                                    case ERROR:
                                        System.out.println("Ошибка в ячейке: " + timeCell.getErrorCellValue());
                                        break;

                                    default:
                                        System.out.println("Пропущена ячейка с неизвестным типом: " + timeCell.getCellType());
                                        break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при парсинге файла", e);
            }
            return priceRecords;
        });
    }

    private Workbook getWorkbook(InputStream inputStream, String fileName) throws Exception {
        if (fileName.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("Неподдерживаемый формат файла. Поддерживаются только .xls и .xlsx.");
        }
    }
}
