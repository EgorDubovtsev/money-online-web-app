package com.web.app.service.impl;


import com.web.app.mapper.LocalUsersMapper;
import com.web.app.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AuthorizationService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final LocalUsersMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.getAllUsers()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .map(userMapper::userEntityToApplicationUser)
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
