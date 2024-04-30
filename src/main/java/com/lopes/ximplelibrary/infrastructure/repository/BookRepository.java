package com.lopes.ximplelibrary.infrastructure.repository;

import com.lopes.ximplelibrary.domain.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}