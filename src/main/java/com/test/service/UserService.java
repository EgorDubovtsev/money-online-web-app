package com.test.service;

import com.test.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getAuthorizedUserByLogin(String login) {
//        return authorizedUserDao.getUser(login)
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
