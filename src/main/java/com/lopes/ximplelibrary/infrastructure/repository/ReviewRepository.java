package com.lopes.ximplelibrary.infrastructure.repository;

import com.lopes.ximplelibrary.domain.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByBookBookId(Long bookId);

}