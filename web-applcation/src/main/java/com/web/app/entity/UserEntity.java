package com.web.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moneyonline.commons.entity.Currency;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty
    private String username;
    @JsonProperty
    private String name;
    @JsonProperty
    private Timestamp birthdate;
    @JsonProperty
    private String password;
    @Transient
    private BigDecimal balance;
    @Transient
    private String accountNumber;
    @Transient
    @JsonProperty
    private Currency currency;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "ROLE")
    private List<String> roles = new ArrayList<>();
    @JsonIgnore
    @Transient
    private ReentrantLock reentrantLock = new ReentrantLock();


    public boolean tryLock() {
        return reentrantLock.tryLock();
    }

    public void lock() {
        reentrantLock.lock();
    }

    public void unlock() {
        reentrantLock.unlock();
    }
}
