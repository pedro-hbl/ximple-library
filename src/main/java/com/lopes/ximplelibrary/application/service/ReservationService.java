package com.lopes.ximplelibrary.application.service;

import com.lopes.ximplelibrary.domain.model.Reservation;

public interface ReservationService {
    Reservation createReservation(Long userId, Long bookId);
}