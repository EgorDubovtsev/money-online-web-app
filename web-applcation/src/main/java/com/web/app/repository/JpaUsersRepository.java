package com.web.app.repository;

import com.web.app.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaUsersRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
