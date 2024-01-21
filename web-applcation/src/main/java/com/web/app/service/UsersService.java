package com.web.app.service;

import com.web.app.entity.UserEntity;
import com.web.app.service.entity.Errors;

import java.util.List;

public interface UsersService {
    void createDefaultUser();

    List<UserEntity> getUsersAvailableForTransfer(String username);

    UserEntity getUserInfoByUsername(String username);

    void createUser(UserEntity user);

    Errors validateUserCreate(UserEntity user);

    UserEntity getUserById(Long userId);
}
