package com.example.service.impl;

import com.example.domain.FileParse.PriceRecord;
import com.example.service.FileParsingService;
import com.example.service.impl.table.TnsTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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
            List<String> labels = new ArrayList<>();

            for (Row row : sheet) {
                List<Double> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            String curStr = cell.getStringCellValue();
                            if (curStr.contains("ЦЕНОВАЯ КАТЕГОРИЯ")) labels.add(curStr);
                            break;
                        case NUMERIC:
                            if (!DateUtil.isCellDateFormatted(cell))
                                rowData.add(cell.getNumericCellValue());
                            break;
                        case FORMULA:
                            CellValue cellValue = evaluator.evaluate(cell);
                            if (Objects.requireNonNull(cellValue.getCellType()) == CellType.NUMERIC)
                                rowData.add(cellValue.getNumberValue());

                            break;
                    }
                }

                if (rowData.size() == 25)
                    result.add(rowData);

                if (!rowData.isEmpty() && rowData.get(0) == 31.0) {
                    TnsTable table = new TnsTable(labels.get(labels.size() - 1));
                    table.setData(result);
                    tnsTables.add(table);
                    result = new ArrayList<>();
                }
            }


//            labels.forEach(System.out::println);
            tnsTables.forEach(curTable -> {
                System.out.println(curTable.getTitle());
                curTable.getData().forEach(System.out::println);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompletableFuture<List<PriceRecord>> parseExcelFile(InputStream inputStream, String fileName) {
        return null;
    }
}
