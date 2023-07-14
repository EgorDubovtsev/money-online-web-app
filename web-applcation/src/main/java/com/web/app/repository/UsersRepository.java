package com.web.app.repository;


import com.web.app.entity.UserEntity;

import java.util.List;

public interface UsersRepository {

    List<UserEntity> getAllUsers();

    UserEntity getUser(String username);

    void createUser(UserEntity user);

    void saveUserChanges(UserEntity user);
}
