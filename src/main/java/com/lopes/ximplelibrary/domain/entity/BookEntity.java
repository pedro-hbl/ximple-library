package com.lopes.ximplelibrary.domain.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    private Long bookId;
    private String authorName;
    private String title;
    private String publisher;
    private Integer publicationYear;
    private Integer totalCopies;
    private Integer reservedCopies;

}