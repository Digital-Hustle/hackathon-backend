package com.example.domain.EnergyPriceData;

import com.example.domain.PriceCategory.PriceCategory;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class EnergyPriceData {
    private Map<String, PriceCategory> categories = new ConcurrentHashMap<>(); // Ключ: "ЦК1", "ЦК2", ...
    private Map<String, Double> transmissionCosts = new ConcurrentHashMap<>(); // Стоимость услуг передачи
    private Map<String, Double> salesMarkup = new ConcurrentHashMap<>(); // Сбытовая надбавка
    private Map<String, Double> infrastructurePayments = new ConcurrentHashMap<>(); // Инфраструктурные платежи
}
