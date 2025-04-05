package com.example.repository;

import com.example.domain.excel.HourlyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourlyDataRepository extends JpaRepository<HourlyData, Long> {
    
}
