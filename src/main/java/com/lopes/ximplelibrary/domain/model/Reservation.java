package com.lopes.ximplelibrary.domain.model;

import java.time.LocalDateTime;

public record Reservation(
        Long reservationId,
        User user,
        Book book,
        LocalDateTime reservationRequestDatetime
){}
