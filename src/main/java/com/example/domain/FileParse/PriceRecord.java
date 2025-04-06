package com.example.domain.FileParse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceRecord {
    private String date;          // Дата
    private int hour;             // Час
    private double price;         // Цена
    private String voltageLevel;  // Уровень напряжения
}
