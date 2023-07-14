package com.transfer.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AuthorizationUsersRepository {
    private static final List<UserDetails> USERS = new ArrayList<>();
    private final PasswordEncoder passwordEncoder;

    public List<UserDetails> getAllServiceUsers() {
        return USERS;
    }

    @PostConstruct
    private void loadUsers() {//TODO:
        UserDetails webUser = User.builder()
                .username("web")
                .password(passwordEncoder.encode("web"))
                .authorities(new SimpleGrantedAuthority("USER"))
                .build();
        USERS.add(webUser);
    }
}
