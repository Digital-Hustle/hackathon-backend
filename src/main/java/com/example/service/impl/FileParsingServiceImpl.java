package com.example.service.impl;

//import com.example.domain.EnergyPriceData.EnergyPriceData;
//import com.example.domain.PriceCategory.PriceCategory;

import com.example.service.FileParsingService;
import com.example.service.impl.table.TnsTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileParsingServiceImpl implements FileParsingService {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\123\\Downloads\\Fakt_1_6_TSK_fevral_TNS_Rostov.xls"; // Путь к файлу


        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new HSSFWorkbook(fis)) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheetAt(0);
            System.out.println(sheet);

            List<TnsTable> tnsTables = new ArrayList<>();
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
                                rowData.add(cell.getNumericCellValue());
//                                System.out.print(cell.getNumericCellValue() + ":(\t");
                            }
                            break;
                        case FORMULA:
                            CellValue cellValue = evaluator.evaluate(cell);
                            if (Objects.requireNonNull(cellValue.getCellType()) == CellType.NUMERIC) {
                                //                                    System.out.print(cellValue.getNumberValue() + ":|\t");
                                rowData.add(cellValue.getNumberValue());
                            }
                            break;
                    }
                }

                if (rowData.size() == 25)
                    result.add(rowData);

                if (!rowData.isEmpty() && rowData.get(0) == 31.0) {
                    TnsTable table = new TnsTable();
                    table.setData(result);
                    tnsTables.add(table);
                    result = new ArrayList<>();
                }
            }


            tnsTables.forEach(table -> {
                System.out.println("New table");
                table.getData().forEach(System.out::println);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
