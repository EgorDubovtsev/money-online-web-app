package com.web.app.service;


import com.web.app.entity.UserEntity;
import com.web.app.repository.UsersRepository;
import com.web.app.service.entity.Errors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static com.web.app.consts.Const.ACCOUNT_NUMBER_SYMBOL_COUNT;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class UsersService {
    private UsersRepository usersRepository;

    public List<UserEntity> getUsersAvailableForTransfer(String username) {
        //todo: TBD
        return usersRepository.getAllUsers()
                .stream()
                .filter(user -> !Objects.equals(user.getUsername(), username))
                .collect(Collectors.toList());
    }

    public UserEntity getUserInfoByUsername(String username) {
        return usersRepository.getUser(username);
    }

    public Errors createUser(UserEntity user) {
        Errors errors = new Errors();

        if (getUserInfoByUsername(user.getUsername()) != null) {
            return errors.addError("Пользователь с таким email уже зарегистрирован.");
        }

        user.setAccountNumber(generateAccountNumber());

        try {
            usersRepository.createUser(user);
        } catch (Exception e) {
            log.warn("Во время создания пользователя просизошла ошибка. User: {}", user);
            log.warn("{}", e);
            errors = errors.undefinedError();
        }

        return errors;
    }

    private String generateAccountNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < ACCOUNT_NUMBER_SYMBOL_COUNT; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
