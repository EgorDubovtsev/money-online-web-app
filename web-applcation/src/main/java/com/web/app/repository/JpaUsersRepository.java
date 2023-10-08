package com.web.app.repository;

import com.web.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface JpaUsersRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
