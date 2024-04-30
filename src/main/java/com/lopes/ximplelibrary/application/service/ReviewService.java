package com.lopes.ximplelibrary.application.service;

import com.lopes.ximplelibrary.domain.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getBookReviews(Long bookId);
    Review createReview(Long userId, Long bookId, Review review);
}