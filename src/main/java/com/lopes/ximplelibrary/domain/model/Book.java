package com.lopes.ximplelibrary.domain.model;

public record Book(
        Long bookId,
        String authorName,
        String title,
        String publisher,
        int publicationYear,
        int totalCopies,
        int reservedCopies
){}