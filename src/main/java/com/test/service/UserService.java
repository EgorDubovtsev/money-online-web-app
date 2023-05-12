package com.test.service;

import com.test.entity.User;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getAuthorizedUserByLogin(String login) {
//        return authorizedUserDao.getList().stream()
//                .filter(value -> value.getLogin().equals(login))
//                .findFirst().orElse(null);
        return new User();
    }

    public boolean addAuthorizedUser(User user) {
        if (user != null) {
//            authorizedUserDao.registrateUser(user);
            return true;
        }
        return false;
    }
}
