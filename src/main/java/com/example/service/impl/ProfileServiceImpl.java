package com.example.service.impl;

import com.example.domain.Profile.History;
import com.example.domain.user.User;
import com.example.service.ProfileService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserService userService;

    @Override
    public History getHistoryById(Long id) {
        User user = userService.getById(id);
        return user.getProfile().getHistory();
    }
}
