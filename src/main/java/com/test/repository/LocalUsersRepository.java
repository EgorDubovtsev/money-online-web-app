package com.test.repository;

import com.google.gson.Gson;
import com.test.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LocalUsersRepository implements UsersRepository {
    private static final Gson MAPPER = new Gson();
    private static List<UserEntity> users;
    private String userFilePath;


    @Override
    public List<UserEntity> getAllUsers() {
        if (users == null) {
            users = getUsersFromFile();
        }
        return users;
    }

    private List<UserEntity> getUsersFromFile() {
        log.debug("Получение пользователей из файла.");
        File usersFile = new File(userFilePath);
        if (!usersFile.exists()) {
            log.error("Некорректный путь к файлу с пользователями. Путь = {}", userFilePath);
            throw new IllegalArgumentException("Некорректный путь к файлу с пользователями.");
        }

        List<UserEntity> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            while (br.ready()) {
                users.add(MAPPER.fromJson(br.readLine(), UserEntity.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("Получены пользователи из файла: {}", users);
        return users;
    }

    @Value("${user.file.path}")
    public void setUserFilePath(String userFilePath) {
        this.userFilePath = userFilePath;
    }

    @PreDestroy
    private void saveAllUsers() {
        log.debug("Сохранение пользователей в файл");//todo:
    }

    @Override
    public UserEntity getUser(String username) {
        return null;
    }

    @Override
    public void createUser(UserEntity user) {
        LocalUsersRepository.users.add(user);
    }

    @Override
    public void saveUserChanges(UserEntity user) {

    }
}
