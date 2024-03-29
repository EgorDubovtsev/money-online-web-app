package com.web.app.repository;

import com.google.gson.Gson;
import com.web.app.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.LongStream;

@Slf4j
@Component
@Profile("local")
public class LocalUsersRepository implements UsersRepository {
    private static final Gson MAPPER = new Gson();
    private static final Long DEFAULT_ID = 1L;
    private static List<UserEntity> users;
    private String userFilePath;


    @Override
    public List<UserEntity> getAllUsers() {
        return users;
    }

    private List<UserEntity> getUsersFromFile() {
        log.debug("Получение пользователей из файла.");
        File usersFile = new File(userFilePath);

        if (!usersFile.exists()) {
            log.error("Некорректный путь к файлу с пользователями. Путь = {}", userFilePath);
            return new ArrayList<>();
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

    @PostConstruct
    private void init() {
        users = getUsersFromFile();
    }

    private void saveAllUsers() throws IOException {
        log.debug("Сохранение пользователей в файл");
        File userFile = new File(userFilePath);

        if (!userFile.exists()) {
            userFile.createNewFile();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile))) {
            for (UserEntity user : users) {
                bw.write(MAPPER.toJson(user));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserEntity getUser(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void createUser(UserEntity user) {
        user.setId(generateId());
        user.setRoles(Collections.singletonList("USER"));
        LocalUsersRepository.users.add(user);
    }

    @Override
    public void saveUserChanges(UserEntity user) {

    }

    private Long generateId() {
        return getAllUsers().stream()
                .flatMapToLong(user -> LongStream.of(user.getId()))
                .max()
                .orElse(DEFAULT_ID);
    }

    @Override
    public UserEntity findById(Long id) {
        return getAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeUserById(Long id) {
        users.remove(findById(id));
    }

    @Override
    public void removeUserByUserName(String username) {
        users.remove(getUser(username));
    }
}
