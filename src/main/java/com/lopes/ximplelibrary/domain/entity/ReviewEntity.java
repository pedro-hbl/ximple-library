package com.lopes.ximplelibrary.domain.entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private BookEntity book;
    private String userComment;
    private int rating;
    private LocalDateTime creationDatetime;

}
