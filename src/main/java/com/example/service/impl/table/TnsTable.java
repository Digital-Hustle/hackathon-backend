package com.example.service.impl.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TnsTable {
    String title;
    List<List<Double>> data;

    public TnsTable(String title) {
        this.title = title;
    }
}
