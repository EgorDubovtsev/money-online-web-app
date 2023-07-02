package com.test.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private Long id;
    private String username;
    private String name;
    private String password;
    private BigDecimal balance;
    private Currency currency;
    private List<String> roles;
}
