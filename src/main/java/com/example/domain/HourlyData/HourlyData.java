package com.example.domain.HourlyData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HourlyData {
    private int day;    // Номер дня
    private int hour;   // Номер часа
    private double volume; // Объем
}
