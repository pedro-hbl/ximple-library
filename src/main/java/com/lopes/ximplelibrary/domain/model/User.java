package com.lopes.ximplelibrary.domain.model;

import java.time.LocalDateTime;


public record User(
        Long userId,
        String name,
        String email,
        String password,
        LocalDateTime creationTime
){}
