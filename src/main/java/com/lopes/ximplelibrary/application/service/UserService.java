package com.lopes.ximplelibrary.application.service;

import com.lopes.ximplelibrary.domain.model.User;

public interface UserService {
    User addUser(User user);
    User getUserById(Long id);
    void deleteUser(Long id);
}
