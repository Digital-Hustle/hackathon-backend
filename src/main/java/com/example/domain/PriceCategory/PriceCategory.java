package com.example.domain.PriceCategory;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class PriceCategory {
    private String name; // Название категории (например, "Первая ценовая категория")
    private Map<String, Double> energyPrices = new ConcurrentHashMap<>(); // Тарифы на электроэнергию (по часам/зонам)
    private Map<String, Double> powerPrices = new ConcurrentHashMap<>(); // Тарифы на мощность
    private double penaltyCoefficient; // Штрафной коэффициент (для ЦК5 и ЦК6)

    public PriceCategory(String name) {
        this.name = name;
    }
}
