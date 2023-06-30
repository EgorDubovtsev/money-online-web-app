package com.test.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;
import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String username;
    private String name;
    private String password;
    private BigDecimal balance;
    private Currency currency;
    private Collection<? extends GrantedAuthority> authorities;
}
