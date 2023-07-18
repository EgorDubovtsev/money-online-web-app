package com.web.app.repository;

import com.web.app.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("prod")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class JpaUsersRepositoryAdapter implements UsersRepository {
    private final JpaUsersRepository usersRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return Streamable.of(usersRepository.findAll()).toList();
    }

    @Override
    public UserEntity getUser(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public void createUser(UserEntity user) {
        usersRepository.save(user);
    }

    @Override
    public void saveUserChanges(UserEntity user) {
        usersRepository.save(user);
    }
}
