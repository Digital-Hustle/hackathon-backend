package com.example.service;

import com.example.domain.Profile.History;

public interface ProfileService {
    History getHistoryById(Long id);
}
