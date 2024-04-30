package com.lopes.ximplelibrary.application.service.converter;

import com.lopes.ximplelibrary.domain.entity.BookEntity;
import com.lopes.ximplelibrary.domain.entity.ReservationEntity;
import com.lopes.ximplelibrary.domain.entity.ReviewEntity;
import com.lopes.ximplelibrary.domain.entity.UserEntity;
import com.lopes.ximplelibrary.domain.model.Book;
import com.lopes.ximplelibrary.domain.model.Reservation;
import com.lopes.ximplelibrary.domain.model.Review;
import com.lopes.ximplelibrary.domain.model.User;

public interface ConverterService {

    BookEntity convertToBookEntity(Book book);

    Book convertToBook(BookEntity bookEntity);

    UserEntity convertToUserEntity(User user);

    User convertToUser(UserEntity userEntity);

    ReservationEntity convertToReservationEntity(Reservation reservation);

    Reservation convertToReservation(ReservationEntity reservationEntity);

    ReviewEntity convertToReviewEntity(Review review);

    Review convertToReview(ReviewEntity reviewEntity);
}