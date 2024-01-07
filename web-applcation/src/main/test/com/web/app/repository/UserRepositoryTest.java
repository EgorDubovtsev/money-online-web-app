package com.web.app.repository;

import com.web.app.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;

@SpringBootTest
@Slf4j
public class UserRepositoryTest {
    @Autowired
    private JpaUsersRepositoryAdapter repositoryAdapter;

    private static UserEntity testUser;

    @BeforeAll
    public static void createTestUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Test User");
        userEntity.setUsername("testUser");
        userEntity.setRoles(Arrays.asList("ADMIN"));
        testUser = userEntity;
    }

    @AfterEach
    @Transactional
    public void deleteTestUser(){
        repositoryAdapter.removeUserByUserName(testUser.getUsername());
    }

    @Test
    @Transactional
    public void JpaUsersRepositoryAdapter_getAllUsers_returnsAllUsers() {
        Assertions.assertEquals(1,repositoryAdapter.getAllUsers().size());
        repositoryAdapter.createUser(testUser);
        log.info("Количество пользователей после сохранения: {}", repositoryAdapter.getAllUsers().size());
        Assertions.assertEquals(2, repositoryAdapter.getAllUsers().size());
    }

    @Test
    @Transactional
    public void JpaUsersRepositoryAdapter_getUser_returnsCorrectUser() {
        repositoryAdapter.createUser(testUser);
        UserEntity createdUser = repositoryAdapter.getUser(testUser.getUsername());
        Assertions.assertTrue(testUser.getName().equals(createdUser.getName()));
        Assertions.assertTrue(testUser.getUsername().equals(createdUser.getUsername()));
    }

    @Test
    @Transactional
    public void JpaUsersRepositoryAdapter_saveUserChanges_savesUserChanges() {
        repositoryAdapter.createUser(testUser);
        testUser.setName("New Name");
//        repositoryAdapter.saveUserChanges(testUser);
        Assertions.assertEquals(testUser.getName(), repositoryAdapter.getUser(testUser.getUsername()).getName());
    }

}
