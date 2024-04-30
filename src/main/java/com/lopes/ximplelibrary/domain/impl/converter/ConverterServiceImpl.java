package com.lopes.ximplelibrary.domain.impl.converter;

import com.lopes.ximplelibrary.application.service.converter.ConverterService;
import com.lopes.ximplelibrary.domain.entity.BookEntity;
import com.lopes.ximplelibrary.domain.entity.ReservationEntity;
import com.lopes.ximplelibrary.domain.entity.ReviewEntity;
import com.lopes.ximplelibrary.domain.entity.UserEntity;
import com.lopes.ximplelibrary.domain.model.Book;
import com.lopes.ximplelibrary.domain.model.Reservation;
import com.lopes.ximplelibrary.domain.model.Review;
import com.lopes.ximplelibrary.domain.model.User;

import org.springframework.stereotype.Service;

@Service
public class ConverterServiceImpl implements ConverterService {

    @Override
    public BookEntity convertToBookEntity(Book book) {
        return new BookEntity(book.bookId(), book.authorName(), book.title(), book.publisher(), book.publicationYear(), book.totalCopies(), book.reservedCopies());
    }

    @Override
    public Book convertToBook(BookEntity bookEntity) {
        return new Book(bookEntity.getBookId(), bookEntity.getAuthorName(), bookEntity.getTitle(), bookEntity.getPublisher(), bookEntity.getPublicationYear(), bookEntity.getTotalCopies(), bookEntity.getReservedCopies());
    }

    @Override
    public UserEntity convertToUserEntity(User user) {
        return new UserEntity(user.userId(), user.name(), user.email(), user.password(), user.creationTime());
    }

    @Override
    public User convertToUser(UserEntity userEntity) {
        return new User(userEntity.getUserId(), userEntity.getName(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getCreationTime());
    }

    @Override
    public ReservationEntity convertToReservationEntity(Reservation reservation) {
        UserEntity userEntity = convertToUserEntity(reservation.user());
        BookEntity bookEntity = convertToBookEntity(reservation.book());
        return new ReservationEntity(reservation.reservationId(), userEntity, bookEntity, reservation.reservationRequestDatetime());
    }

    @Override
    public Reservation convertToReservation(ReservationEntity reservationEntity) {
        User user = convertToUser(reservationEntity.getUser());
        Book book = convertToBook(reservationEntity.getBook());
        return new Reservation(reservationEntity.getReservationId(), user, book, reservationEntity.getReservationRequestDatetime());
    }

    @Override
    public ReviewEntity convertToReviewEntity(Review review) {
        UserEntity userEntity = convertToUserEntity(review.user());
        BookEntity bookEntity = convertToBookEntity(review.book());
        return new ReviewEntity(review.reviewId(), userEntity, bookEntity, review.userComment(), review.rating(), review.creationDatetime());
    }

    @Override
    public Review convertToReview(ReviewEntity reviewEntity) {
        User user = convertToUser(reviewEntity.getUser());
        Book book = convertToBook(reviewEntity.getBook());
        return new Review(reviewEntity.getReviewId(), user, book, reviewEntity.getUserComment(), reviewEntity.getRating(), reviewEntity.getCreationDatetime());
    }
}