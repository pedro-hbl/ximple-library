package com.lopes.ximplelibrary.domain.impl;

import com.lopes.ximplelibrary.application.service.BookService;
import com.lopes.ximplelibrary.application.service.converter.ConverterService;
import com.lopes.ximplelibrary.domain.entity.BookEntity;
import com.lopes.ximplelibrary.domain.model.AvailabilityStatus;
import com.lopes.ximplelibrary.domain.model.Book;
import com.lopes.ximplelibrary.infrastructure.repository.BookRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final Counter bookByIdCounter;
    private final Counter bookAvailabilityCounter;
    private final Counter createBookCounter;
    private final ConverterService converterService;

    public BookServiceImpl(BookRepository bookRepository, MeterRegistry meterRegistry, ConverterService converterService) {
        this.bookRepository = bookRepository;
        this.bookByIdCounter = meterRegistry.counter("counter.getBookById");
        this.bookAvailabilityCounter = meterRegistry.counter("counter.getBookAvailability");
        this.createBookCounter = meterRegistry.counter("counter.createBook");
        this.converterService = converterService;
    }

    @Transactional
    @Override
    public Book createBook(Book bookDetails) {
        try {
            log.info("Creating a new book: {}", bookDetails.toString());
            createBookCounter.increment();

            BookEntity newBookEntity = converterService.convertToBookEntity(bookDetails);
            BookEntity savedBookEntity = bookRepository.save(newBookEntity);
            Book savedBook = converterService.convertToBook(savedBookEntity);

            log.info("Created Book: {}", savedBook.toString());
            return savedBook;
        } catch (Exception e) {
            log.error("Error while creating book: {}", bookDetails.toString(), e);
            throw e;
        }
    }

    @Cacheable(cacheNames = "book", key = "#id")
    @Override
    public Book getBookById(Long id) {
        log.info("Fetching book with id {}", id);
        try {
            bookByIdCounter.increment();
            BookEntity bookEntity = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found for id " + id));
            return converterService.convertToBook(bookEntity);
        } catch (Exception e) {
            log.error("Error while fetching book with id {}", id, e);
            throw e;
        }
    }

    @Cacheable(cacheNames = "availabilityStatus", key = "#id")
    @Override
    public AvailabilityStatus getBookAvailability(Long id) {
        log.info("Fetching book availability for id {}", id);
        try {
            bookAvailabilityCounter.increment();
            Book book = getBookById(id);
            if (book.totalCopies() - book.reservedCopies() > 0) {
                return AvailabilityStatus.AVAILABLE;
            } else {
                return AvailabilityStatus.RESERVED;
            }
        } catch (Exception e) {
            log.error("Error fetching book availability for id {}", id, e);
            throw e;
        }
    }

    @Transactional
    @CacheEvict(cacheNames = "bookReviews", allEntries = true)
    @Override
    public Book updateBook(Long bookId, Book bookDetails) {
        BookEntity bookEntityToUpdate = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found"));

        Book bookToUpdate = converterService.convertToBook(bookEntityToUpdate);

        log.info("Updating book: {}", bookToUpdate);

        Book updatedBook = bookDetails;

        BookEntity updatedBookEntity = converterService.convertToBookEntity(updatedBook);
        BookEntity savedBookEntity = bookRepository.save(updatedBookEntity);

        Book savedBook = converterService.convertToBook(savedBookEntity);

        log.info("Updated book saved to database: {}", savedBook);

        return savedBook;
    }

    @CacheEvict(cacheNames = {"book", "availabilityStatus"}, key = "#id")
    public void evictCache(Long id) {
        log.info("Evicting cache for the book with id {}", id);
    }
}