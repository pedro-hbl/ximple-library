package com.lopes.ximplelibrary.domain.impl;

import com.lopes.ximplelibrary.application.service.ReservationService;
import com.lopes.ximplelibrary.application.service.converter.ConverterService;
import com.lopes.ximplelibrary.domain.entity.BookEntity;
import com.lopes.ximplelibrary.domain.entity.ReservationEntity;
import com.lopes.ximplelibrary.domain.entity.UserEntity;
import com.lopes.ximplelibrary.domain.model.Book;
import com.lopes.ximplelibrary.domain.model.Reservation;
import com.lopes.ximplelibrary.domain.model.User;
import com.lopes.ximplelibrary.infrastructure.repository.BookRepository;
import com.lopes.ximplelibrary.infrastructure.repository.ReservationRepository;
import com.lopes.ximplelibrary.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;


@Service
public class ReservationServiceImpl implements ReservationService {
    private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;
    private final Counter reservationCreationCounter;
    private final ConverterService converterService;

    public ReservationServiceImpl(UserRepository userRepository,
                                  BookRepository bookRepository, ReservationRepository reservationRepository, MeterRegistry meterRegistry, ConverterService converterService) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
        this.reservationCreationCounter = meterRegistry.counter("counter.createReservation");
        this.converterService = converterService;
    }

    @Transactional
    @Override
    public Reservation createReservation(Long userId, Long bookId) {
        log.info("Creating a reservation for bookId:{}, userId:{}", bookId, userId);
        try {
            reservationCreationCounter.increment();

            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found for id " + userId));
            BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found for id " + bookId));

            ReservationEntity newReservation = new ReservationEntity(null, userEntity, bookEntity, LocalDateTime.now());
            ReservationEntity savedReservation = reservationRepository.save(newReservation);

            log.info("Successfully created a reservation for bookId:{}, userId:{}", bookId, userId);

            return converterService.convertToReservation(savedReservation);
        } catch (Exception e) {
            log.error("Error while creating a reservation for bookId:{}, userId:{}", bookId, userId, e);
            throw e;
        }
    }
}