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
import java.util.Objects;
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
    @Column(unique = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUsername(),
                that.getUsername()) && Objects.equals(getName(),
                that.getName()) && Objects.equals(getBirthdate(), that.getBirthdate())
                && Objects.equals(getPassword(), that.getPassword())
                && Objects.equals(getBalance(), that.getBalance())
                && Objects.equals(getAccountNumber(), that.getAccountNumber())
                && getCurrency() == that.getCurrency()
                && (Objects.equals(getRoles(), that.getRoles()) || getRoles().containsAll(that.getRoles()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getName(), getBirthdate(), getPassword(), getBalance(), getAccountNumber(), getCurrency(), getRoles());
    }
}
