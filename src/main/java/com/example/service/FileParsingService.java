package com.example.service;


import com.example.domain.FileParse.PriceRecord;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface FileParsingService {
    CompletableFuture<List<PriceRecord>> parseExcelFile(InputStream inputStream, String fileName);
}
