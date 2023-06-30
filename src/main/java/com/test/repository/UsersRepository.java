package com.test.repository;

import com.test.entity.UserEntity;

import java.util.List;

public interface UsersRepository {

    List<UserEntity> getAllUsers();

    UserEntity getUser(String username);

    void createUser(UserEntity user);

    void saveUserChanges(UserEntity user);
}
