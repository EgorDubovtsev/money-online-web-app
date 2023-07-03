package com.test.service;

import com.test.entity.UserEntity;
import com.test.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
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

    public void createUser(UserEntity user){
        usersRepository.createUser(user);
    }
}
