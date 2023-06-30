package com.test.mapper;

import com.test.entity.ApplicationUser;
import com.test.entity.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LocalUsersMapper {
    @Mapping(target = "password", qualifiedByName = "encoder")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "authorities", expression = "java(getRoles())")
    ApplicationUser userEntityToApplicationUser(UserEntity user);

    @Named("encoder")
    default String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    default HashSet<GrantedAuthority> getRoles() {
        HashSet<GrantedAuthority> defaultAuthority = new HashSet<>();
        defaultAuthority.add(new SimpleGrantedAuthority("USER"));
        defaultAuthority.add(new SimpleGrantedAuthority("ADMIN"));
        return defaultAuthority;
    }

//    @Mapping(target = "role", source = "roles")
//    @Mapping(target = "username", source = "username")
//    @Mapping(target = "password", source = "password")
//    UserEntity userDetailsToUser(User user);
}
