package com.lopes.ximplelibrary.application.service;

import com.lopes.ximplelibrary.domain.model.AvailabilityStatus;
import com.lopes.ximplelibrary.domain.model.Book;

public interface BookService {
    Book createBook(Book bookDetails);
    Book getBookById(Long id);
    AvailabilityStatus getBookAvailability(Long id);
    Book updateBook(Long bookId, Book bookDetails);
}
