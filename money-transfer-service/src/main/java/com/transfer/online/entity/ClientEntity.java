package com.transfer.online.entity;

import com.moneyonline.commons.entity.Currency;
import com.transfer.online.dto.ClientDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true)
    private Long clientId;
    private String account;
    private BigDecimal balance;
    private String currency;

    public ClientEntity(ClientDto clientDto) {
        this.clientId = clientDto.getClientId();
        this.account = clientDto.getAccount();
        this.balance = clientDto.getBalance();
        this.currency = clientDto.getCurrency();
    }
}
