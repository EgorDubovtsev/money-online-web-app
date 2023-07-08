package com.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private Long id;
    @JsonProperty
    private String username;
    @JsonProperty
    private String name;
    @JsonProperty
    private Timestamp birthdate;
    @JsonProperty
    private String password;
    private BigDecimal balance;
    @JsonProperty
    private Currency currency;
    private List<String> roles;
}
