package com.lopes.ximplelibrary.adapter.controller;

import com.lopes.ximplelibrary.application.service.BookService;
import com.lopes.ximplelibrary.application.service.ReviewService;
import com.lopes.ximplelibrary.domain.model.AvailabilityStatus;
import com.lopes.ximplelibrary.domain.model.Book;
import com.lopes.ximplelibrary.domain.model.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ximple-api/books")
public class BookController {

    private final BookService bookService;
    private final ReviewService reviewService;

    public BookController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book bookDetails) {
        Book newBook = bookService.createBook(bookDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long bookId, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(bookId, bookDetails);

        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<AvailabilityStatus> checkBookAvailability(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookAvailability(id));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getBookReviews(@PathVariable Long id){
        return ResponseEntity.ok(reviewService.getBookReviews(id));
    }
}