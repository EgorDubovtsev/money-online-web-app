package com.test.config;

import com.test.mapper.LocalUsersMapper;
import com.test.repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class UsersConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Profile("local")
    @Bean
    public UserDetailsService users(BCryptPasswordEncoder encoder, UsersRepository repository, LocalUsersMapper userMapper) {
        List<UserDetails> users = repository.getAllUsers()
                .stream()
                .map(userMapper::userEntityToApplicationUser)
                .collect(Collectors.toList());

        return new InMemoryUserDetailsManager(users);
    }
}
