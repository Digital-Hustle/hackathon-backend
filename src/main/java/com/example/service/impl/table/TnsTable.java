package com.example.service.impl.table;

import java.util.ArrayList;
import java.util.List;

public class TnsTable {
    List<List<Double>> data;

    public TnsTable() {
    }

    public TnsTable(List<List<Double>> data) {
        this.data = data;
    }

    public List<List<Double>> getData() {
        return data;
    }

    public void setData(List<List<Double>> data) {
        this.data = data;
    }
}
