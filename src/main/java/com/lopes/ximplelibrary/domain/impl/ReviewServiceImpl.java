package com.lopes.ximplelibrary.domain.impl;

import com.lopes.ximplelibrary.application.service.ReviewService;
import com.lopes.ximplelibrary.application.service.converter.ConverterService;
import com.lopes.ximplelibrary.domain.entity.BookEntity;
import com.lopes.ximplelibrary.domain.entity.ReviewEntity;
import com.lopes.ximplelibrary.domain.entity.UserEntity;
import com.lopes.ximplelibrary.domain.model.Book;
import com.lopes.ximplelibrary.domain.model.Review;
import com.lopes.ximplelibrary.domain.model.User;
import com.lopes.ximplelibrary.infrastructure.repository.BookRepository;
import com.lopes.ximplelibrary.infrastructure.repository.ReviewRepository;
import com.lopes.ximplelibrary.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final Counter reviewCreationCounter;
    private final Timer reviewRetrievalTimer;
    private final CacheManager cacheManager;
    private final ConverterService converterService;

    public ReviewServiceImpl(UserRepository userRepository,
                             BookRepository bookRepository,
                             ReviewRepository reviewRepository, MeterRegistry meterRegistry, CacheManager cacheManager, ConverterService converterService) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.reviewCreationCounter = meterRegistry.counter("counter.createReview");
        this.reviewRetrievalTimer = meterRegistry.timer("timer.getBookReviews");
        this.cacheManager = cacheManager;
        this.converterService = converterService;
    }

    @Cacheable(cacheNames = "bookReviews", key = "#bookId")
    @Override
    public List<Review> getBookReviews(Long bookId) {
        log.info("Fetching reviews for bookId:{}", bookId);
        try {
            return reviewRetrievalTimer.record(() -> {
                List<ReviewEntity> reviewEntities = reviewRepository.findAllByBookBookId(bookId);
                return reviewEntities.stream().map(converterService::convertToReview).collect(Collectors.toList());
            });
        } catch (Exception e) {
            log.error("Error while fetching reviews for bookId:{}", bookId, e);
            throw e;
        }
    }

    @Transactional
    @CacheEvict(cacheNames = "bookReviews", allEntries = true)
    @Override
    public Review createReview(Long userId, Long bookId, Review review) {
        log.info("Creating a review for bookId:{}, userId:{}", bookId, userId);
        try {
            reviewCreationCounter.increment();
            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found for id " + userId));
            BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found for id " + bookId));

            ReviewEntity newReviewEntity = new ReviewEntity(null, userEntity, bookEntity, review.userComment(), review.rating(), LocalDateTime.now());
            ReviewEntity savedReviewEntity = reviewRepository.save(newReviewEntity);
            log.info("Successfully created a review for bookId:{}, userId:{}", bookId, userId);
            return converterService.convertToReview(savedReviewEntity);
        } catch (Exception e) {
            log.error("Error while creating a review for bookId:{}, userId:{},", bookId, userId, e);
            throw e;
        }
    }

    public void evictAllCaches() {
        for (String name : cacheManager.getCacheNames()) {
            cacheManager.getCache(name).clear();
        }
    }
}