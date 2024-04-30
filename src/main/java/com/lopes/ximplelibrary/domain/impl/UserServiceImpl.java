package com.lopes.ximplelibrary.domain.impl;

import com.lopes.ximplelibrary.application.service.UserService;
import com.lopes.ximplelibrary.application.service.converter.ConverterService;
import com.lopes.ximplelibrary.domain.entity.UserEntity;
import com.lopes.ximplelibrary.domain.model.User;
import com.lopes.ximplelibrary.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ConverterService converterService;
    private final Counter userCreationCounter;
    private final Counter userRetrievalCounter;

    public UserServiceImpl(UserRepository userRepository, ConverterService converterService, MeterRegistry meterRegistry) {
        this.userRepository = userRepository;
        this.converterService = converterService;
        this.userCreationCounter = meterRegistry.counter("counter.addUser");
        this.userRetrievalCounter = meterRegistry.counter("counter.getUserById");
    }

    @Transactional
    @Override
    public User addUser(User user) {
        log.info("Adding user with id {}", user.userId());
        try {
            userCreationCounter.increment();
            UserEntity userEntity = converterService.convertToUserEntity(user);
            UserEntity savedUserEntity = userRepository.save(userEntity);
            return converterService.convertToUser(savedUserEntity);
        } catch (Exception e) {
            log.error("Error while adding user with id {}", user.userId(), e);
            throw e;
        }
    }

    @Override
    public User getUserById(Long id) {
        log.info("Fetching user with id {}", id);
        try {
            userRetrievalCounter.increment();
            UserEntity userEntity = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found for id " + id));
            return converterService.convertToUser(userEntity);
        } catch (Exception e) {
            log.error("Error while fetching user with id {}", id, e);
            throw e;
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with id {}", id);
        if (userRepository.existsById(id)) {
            try {
                userRepository.deleteById(id);
            } catch (Exception e) {
                log.error("Error while deleting user with id{}", id, e);
                throw e;
            }
        } else {
            throw new RuntimeException("User not found for id " + id);
        }
    }
}