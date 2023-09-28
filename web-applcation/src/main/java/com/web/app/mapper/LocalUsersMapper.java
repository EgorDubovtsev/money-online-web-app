package com.web.app.mapper;


import com.web.app.entity.ApplicationUser;
import com.web.app.entity.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LocalUsersMapper {
    @Mapping(target = "password", qualifiedByName = "encoder")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "authorities", expression = "java(getRoles(user.getRoles()))")
    ApplicationUser userEntityToApplicationUser(UserEntity user);

    @Named("encoder")
    default String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    default HashSet<GrantedAuthority> getRoles(List<String> roles) {
        if (roles == null) {
            return new HashSet<>();
        }
        HashSet<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role))
        );
        return authorities;
    }

}
