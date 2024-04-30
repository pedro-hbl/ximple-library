package com.lopes.ximplelibrary.adapter.controller;

import com.lopes.ximplelibrary.application.service.ReservationService;
import com.lopes.ximplelibrary.application.service.ReviewService;
import com.lopes.ximplelibrary.application.service.UserService;
import com.lopes.ximplelibrary.domain.model.Reservation;
import com.lopes.ximplelibrary.domain.model.Review;
import com.lopes.ximplelibrary.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ximple-api/users")
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final ReviewService reviewService;

    public UserController(UserService userService, ReservationService reservationService, ReviewService reviewService) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/book/{bookId}")
    public ResponseEntity<Reservation> reserveBook(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(userId, bookId));
    }

    @PostMapping("/{userId}/review/{bookId}")
    public ResponseEntity<Review> reviewBook(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody Review review) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(userId, bookId, review));
    }
}