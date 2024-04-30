package com.lopes.ximplelibrary.domain.model;

import java.time.LocalDateTime;

public record Review (
        Long reviewId,
        User user,
        Book book,
        String userComment,
        int rating,
        LocalDateTime creationDatetime
){}
