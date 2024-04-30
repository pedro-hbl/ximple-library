package com.lopes.ximplelibrary.infrastructure.repository;

import com.lopes.ximplelibrary.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}